package com.alex.musicart.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FooterTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String tagText = "<div class=\"sticky-bottom\"><footer class=\"py-3 my-4 border-top\"><p class=\"text-center text-muted \">© 2022 Alex Musicart, Inc</p></footer></div>";
            out.write(tagText);
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}
