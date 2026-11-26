public class IssueRecord{
    private int bookId;
    private int memberId;
    private String bookName;
    private boolean active;

    public IssueRecord(int bookId,int memberId,String bookName){
        this.bookId=bookId;
        this.memberId=memberId;
        this.bookName=bookName;
        active=true;
    }
    public int getBookId(){
        return bookId;
    }
    public int getMemberId(){
        return memberId;
    }
    public String getBookName(){
        return bookName;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active=active;
    }
    public void displayIssueDetails(){
        System.out.println("Member ID: "+memberId);
        System.out.println("Book ID: "+bookId);
        System.out.println("Book Name: "+bookName);
        System.out.println("Issue Status: "+(active?"Active":"Returned"));
    }
}