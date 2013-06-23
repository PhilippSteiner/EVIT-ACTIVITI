/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

/**
 *
 * @author mike
 */
public enum MailType {

    MAIL_TYPE_INVITATION("Einladung"),
    MAIL_TYPE_RESET("Passwort Reset"),
    MAIL_TYPE_LOGIN("Login"),
    MAIL_TYPE_REMINDER("Urgenz"),
    MAIL_TYPE_TEST("Test");

    private MailType(final String text) {
        this.text = text;
    }
    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
