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
            //Integer total_records = (Integer) request.getAttribute("total_records");
            //Integer page_size = (Integer) request.getAttribute("page_size");
            //Integer current_page = (Integer) request.getAttribute( "current_page" );
            Integer current_page = page_number;
            Integer total_records = next_pages;
            Integer page_size = 2;
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
            // показывать ли полностью все ссылки на страницы слева от текущей, или вставить многоточие
            boolean showAllPrev;
            // показывать ли полностью все ссылки на страницы справа от текущей, или вставить многоточие
            boolean showAllNext;
            showAllPrev = N_PAGES_FIRST >= (current_page - N_PAGES_PREV);
            showAllNext = current_page + N_PAGES_NEXT >= last_page - N_PAGES_LAST;
            //"<%-- show left pages --%>" +
            String tagText = "<div style=\"text-align: center\">";
            if (current_page > 1) {
                tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + (current_page - 1 >= 1 ? current_page - 1 : 1) + "\" class=\"prev\">&lt; Prev</a>";
            }
            //if (showAllPrev) {
                if (current_page > 1) {
                    for (int i = 1; i < current_page; i++) {
                        tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                    }
                }
            /*} else {
                for (int i = 1; i <= N_PAGES_FIRST; i++) {
                    tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                }
                tagText = tagText + "<span style=\"margin-right: 5px\"> ... </span>";
                for (int i = current_page - N_PAGES_PREV; i <= current_page; i++) {
                    tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                }
            }*/

            //"    <%-- show current page --%>\n" +
            tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + current_page + "\" class=\"current\">" + current_page + "</a>\n";

            //"    <%-- show right pages --%>\n" +
            //if (showAllNext) {
                for (int i = current_page + 1; i <= last_page; i++) {
                    tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                }
            /*} else {
                for (int i = current_page + 1; i <= (current_page + 1 + N_PAGES_NEXT); i++) {
                    tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                }
                tagText = tagText + "<span style=\"margin-right: 5px\"> ... </span>";
                for (int i = last_page - N_PAGES_LAST; i <= last_page; i++) {
                    tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + i + "\">" + i + "</a>";
                }
            }*/

            if (current_page < last_page) {
                //tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + (current_page + 1 > last_page ? last_page : current_page + 1) + "\" class=\"next\">Next &gt;</a>";
                tagText = tagText + "<a href=\"" + url + "to_main&page_number=" + (current_page + 1 > last_page ? last_page : current_page + 1) + "\" class=\"next\">Next &gt;</a>";
            }
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
