package net.codjo.ads;
/**
 *
 */
public class AccountLockedException extends AdsException {

    public AccountLockedException(String message) {
        super(message);
    }


    public AccountLockedException(Throwable cause) {
        super(cause);
    }
}
