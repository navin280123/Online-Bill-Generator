package ProjectGUI;

public class InvoiceException extends Exception {

    public InvoiceException(Throwable t)
    {
        super(t);
    }

    public InvoiceException(String message, Throwable t)
    {
        super(message, t);
    }

}