package com.alex.musicart.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private Integer page_number;
    private String url;
    private int next_pages;

    public Integer getPage_number() {
        return page_number;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNext_pages() {
        return next_pages;
    }

    public void setNext_pages(int next_pages) {
        this.next_pages = next_pages;
    }

    @Override
    public int doStartTag() throws JspException {
        if (page_number != null) {
            Integer current_page = page_number;
            Integer total_records = next_pages;
            Integer page_size = 12;
            int pages = total_records / page_size;
            if (total_records % page_size > 0) {
                pages++;
            }
            int last_page = current_page + pages;
            // сколько ссылок отображается начиная с самой первой (не может быть установлено в 0)
            final int N_PAGES_FIRST = 1;
            // сколько ссылок отображается слева от текущей (может быть установлено в 0)
            final int N_PAGES_PREV = 1;
            // сколько ссылок отображается справа от текущей (может быть установлено в 0)
            final int N_PAGES_NEXT = 1;
            // сколько ссылок отображается в конце списка страниц (не может быть установлено в 0)
            final int N_PAGES_LAST = 1;
            if (N_PAGES_FIRST < 1 || N_PAGES_LAST < 1) throw new AssertionError();
            String tagText = "<div class=\"d-flex justify-content-center\">";
            tagText = tagText + "<ul class=\"pagination\">";
            if (current_page > 1) {
                tagText = tagText + "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "to_main&page_number=" + (current_page - 1 >= 1 ? current_page - 1 : 1) + "\"><span aria-hidden=\"true\">&laquo;</span></a></li>";
            }
            if (current_page > 1) {
                for (int i = 1; i < current_page; i++) {
                    tagText = tagText + "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "to_main&page_number=" + i + "\"><span aria-hidden=\"true\">" + i + "</span></a></li>";
                }
            }
            //"    <%-- show current page --%>\n" +
            tagText = tagText + "<li class=\"page-item active\"><a class=\"page-link\" href=\"" + url + "to_main&page_number=" + current_page + "\"><span aria-hidden=\"true\">" + current_page + "</span></a></li>";
            //"    <%-- show right pages --%>\n" +
            for (int i = current_page + 1; i <= last_page; i++) {
                tagText = tagText + "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "to_main&page_number=" + i + "\"><span aria-hidden=\"true\">" + i + "</span></a></li>";
            }
            if (current_page < last_page) {
                tagText = tagText + "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "to_main&page_number=" + (current_page + 1 > last_page ? last_page : current_page + 1) + "\"><span aria-hidden=\"true\">&raquo;</span></a></li>";
            }
            tagText = tagText + "</ul>";
            tagText = tagText + "</div>";
            try {
                JspWriter out = pageContext.getOut();
                out.write(tagText);
            } catch (IOException e) {
                throw new JspTagException(e);
            }
        }
        return SKIP_BODY;
    }
}
