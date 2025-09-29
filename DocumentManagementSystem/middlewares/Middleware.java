package DocumentManagementSystem.middlewares;

import DocumentManagementSystem.Payload;

public abstract class Middleware {

    private Middleware nextMiddleware;

    public static Middleware linkMiddleware(Middleware first, Middleware...chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.nextMiddleware = nextInChain;
            head = nextInChain;
        }
        return head;
    }

    public abstract boolean check(Payload payload);

    protected boolean checkNext(Payload payload) {
        if(nextMiddleware==null) {
            return true;
        }
        return nextMiddleware.check(payload);
    }

}
