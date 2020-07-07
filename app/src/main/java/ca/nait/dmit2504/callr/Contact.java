package ca.nait.dmit2504.callr;

public class Contact {

    private long mId;
    private String mName;
    private String mNumber;
    private String mIgnore;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String  getIgnore() {
        return mIgnore;
    }

    public void setIgnore(String ignore) {
        mIgnore = ignore;
    }
}
