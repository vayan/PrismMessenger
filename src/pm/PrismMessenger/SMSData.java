package pm.PrismMessenger;

/**
 * Created by Lan on 08/02/2015.
 */

enum Type {
    RECEIVED ("1"),
    SEND ("2");

    private final String name;

    private Type(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
        return name;
    }
}

public class SMSData
{
    private String id;
    private String contactName;
    private String contactNumber;
    private String body;
    private String owner;//1 = received, 2 = send

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
