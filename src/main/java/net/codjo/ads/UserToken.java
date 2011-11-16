package net.codjo.ads;
/**
 *
 */
public class UserToken {
    private final de.dit.ads.auth.UserToken userToken;


    UserToken(de.dit.ads.auth.UserToken userToken) {
        this.userToken = userToken;
    }


    public String getUserId() {
        return userToken.getUserId();
    }


    public String getUserName() {
        return userToken.getCommonName();
    }


    @SuppressWarnings({"RedundantIfStatement"})
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserToken userToken1 = (UserToken)o;

        if (userToken != null ? !userToken.equals(userToken1.userToken) : userToken1.userToken != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return userToken != null ? userToken.hashCode() : 0;
    }
}
