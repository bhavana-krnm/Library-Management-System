import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

class Library extends LibraryOperations{
    private ArrayList<Book> books=new ArrayList<>();
    private ArrayList<LibraryMember> members=new ArrayList<>();
    private HashMap<Integer,Book> bookMap=new HashMap<>();
    private LinkedHashMap<Integer,IssueRecord> issueRecords=new LinkedHashMap<>();
    private HashSet<Integer> issuedBookIds=new HashSet<>();
    private int nextMemberId=1001;
    private int nextIssueId=1;
    @Override
    public void addBook(Book book){
        if(bookMap.containsKey(book.getBookId())){
            System.out.println("Book ID already exists.");
            return;
        }
        books.add(book);
        bookMap.put(book.getBookId(),book);
        System.out.println("Book Added Successfully!");
    }
    @Override
    public void displayBooks(){
        if(books.isEmpty()){
            System.out.println("No books registered.");
            return;
        }
        for(Book book:books){
            book.displayBookDetails();
            System.out.println();
        }
        System.out.println("Total Books: "+books.size());
        for(Book book:books){
            if(book.getAvailableCopies()==0){
                System.out.println("Unavailable Book: "+book.getBookName());
            }
        }
    }
    public void searchBook(int bookId){
        Book book=bookMap.get(bookId);
        if(book==null){
            System.out.println("Book not found.");
        }
        else{
            book.displayBookDetails();
        }
    }
    public void registerStudent(String name,String contact,String department,int year){
        members.add(new Student(nextMemberId++,name,contact,department,year));
        System.out.println("Student Registered Successfully!");
    }
    public void registerFaculty(String name,String contact,String department,String designation){
        members.add(new Faculty(nextMemberId++,name,contact,department,designation));
        System.out.println("Faculty Registered Successfully!");
    }
    public void displayMembers(){
        if(members.isEmpty()){
            System.out.println("No members registered.");
            return;
        }
        for(LibraryMember member:members){
            member.displayMemberDetails();
            System.out.println();
        }
    }
    public void searchMember(int memberId){
        for(LibraryMember member:members){
            if(member.getMemberId()==memberId){
                member.displayMemberDetails();
                return;
            }
        }
        System.out.println("Member not found.");
    }
    private boolean memberExists(int memberId){
        for(LibraryMember member:members){
            if(member.getMemberId()==memberId){
                return true;
            }
        }
        return false;
    }
    @Override
    public void issueBook(int bookId,int memberId){
        Book book=bookMap.get(bookId);
        if(!memberExists(memberId)){
            System.out.println("Member not found.");
            return;
        }
        if(book==null){
            System.out.println("Book not found.");
            return;
        }
        if(book.getAvailableCopies()<=0){
            System.out.println("No copies available.");
            return;
        }
        book.setAvailableCopies(book.getAvailableCopies()-1);
        IssueRecord record=new IssueRecord(bookId,memberId,book.getBookName());
        issueRecords.put(nextIssueId++,record);
        issuedBookIds.add(bookId);
        System.out.println("Book Issued Successfully!");
        System.out.println("Member ID: "+memberId);
        System.out.println("Book: "+book.getBookName());
        System.out.println("Available Copies: "+book.getAvailableCopies());
    }
    @Override
    public void returnBook(int bookId,int memberId){
        for(IssueRecord record:issueRecords.values()){
            if(record.getBookId()==bookId&&record.getMemberId()==memberId&&record.isActive()){
                Book book=bookMap.get(bookId);
                if(book!=null){
                    book.setAvailableCopies(book.getAvailableCopies()+1);
                }
                record.setActive(false);
                boolean stillIssued=false;
                for(IssueRecord other:issueRecords.values()){
                    if(other.getBookId()==bookId&&other.isActive()){
                        stillIssued=true;
                        break;
                    }
                }
                if(!stillIssued){
                    issuedBookIds.remove(bookId);
                }
                System.out.println("Book Returned Successfully!");
                return;
            }
        }
        System.out.println("No active issue record found for this member and book.");
    }
    public void displayIssuedBooks(boolean activeOnly){
        if(issueRecords.isEmpty()){
            System.out.println("No issue records found.");
            return;
        }
        for(IssueRecord record:issueRecords.values()){
            if(!activeOnly||record.isActive()){
                record.displayIssueDetails();
                System.out.println();
            }
        }
    }
    public void generateReport(){
        int totalCopies=0;
        int activeIssues=0;
        System.out.println("Total Books Registered: "+books.size());
        System.out.println("Total Registered Members: "+members.size());
        for(IssueRecord record:issueRecords.values()){
            if(record.isActive()){
                activeIssues++;
            }
        }
        for(Book book:books){
            totalCopies+=book.getAvailableCopies();
            if(book.getAvailableCopies()==0){
                System.out.println("Unavailable Book: "+book.getBookName());
            }
        }
        System.out.println("Total Active Issue Records: "+activeIssues);
        System.out.println("Total Available Book Copies: "+totalCopies);
    }
}