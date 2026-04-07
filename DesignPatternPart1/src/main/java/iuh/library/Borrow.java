interface Borrow {
    String getDescription();
}

class BasicBorrow implements Borrow {
    public String getDescription() {
        return "Mượn cơ bản";
    }
}

abstract class BorrowDecorator implements Borrow {
    protected Borrow borrow;
    public BorrowDecorator(Borrow b) { this.borrow = b; }
}

class ExtendBorrow extends BorrowDecorator {
    public ExtendBorrow(Borrow b) { super(b); }
    public String getDescription() {
        return borrow.getDescription() + ", Gia hạn";
    }
}

class SpecialEdition extends BorrowDecorator {
    public SpecialEdition(Borrow b) { super(b); }
    public String getDescription() {
        return borrow.getDescription() + ", Bản đặc biệt";
    }
}