package servlets.taglibs;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import models.Person;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

@Slf4j
public class IterateList extends TagSupport {
    @Setter
    private String collection;
    @Setter
    private String buttonType;
    @Setter
    private String buttonName;

    @Override
    public int doStartTag() {
        List<Person> list = (List<Person>) pageContext.findAttribute(collection);
        if (list == null) {
            log.info("Error in taglib");
        } else {
            JspWriter out = pageContext.getOut();
            try {
                for (Person user : list) {
                    out.print("<div class='result-entity'>");
                    out.print("<a href=\"/Profile/" + user.getId() + "\">");
                    out.print("<img class=\"entity-img\" src=\"/images/" + user.getId() + ".jpg\"/>\n");
                    out.print("</a>");
                    out.print("<p class='entity-name'>" + user.getFirstName() + "</p>\n");
                    out.print("<p class='entity-name'>" + user.getLastName() + "</p>\n");
                    if (!buttonType.equals("searchResult")) {
                        out.print("<form action=\"/AddRemoveFriend\" method=\"post\">");
                        out.print("<input type=\"hidden\" name=\"user_id\" value=\"" + user.getId() + "\">");
                        out.print("<button class=\"entity-button\" name=\"status\"" +
                                " value=\"");
                        switch (buttonType) {
                            case "blocked": {
                                out.print("-1");
                                break;
                            }
                            case "followers": {
                                out.print("1");
                                break;
                            }
                            case "following": {
                                out.print("0");
                                break;
                            }
//                    default:{
//                        out.print("0\" type=\"hidden");
//                    }
                        }
                        out.print("\">" + buttonName + "</button>");
                        out.print("</form>");
                    }
                    out.print("</div>");
                }
            } catch (IOException e) {
                log.error("Taglib ListIterate: ", e);
            }
        }
        return SKIP_BODY;
    }
}
