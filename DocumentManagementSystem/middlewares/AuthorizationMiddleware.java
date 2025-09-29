package DocumentManagementSystem.middlewares;

import DocumentManagementSystem.Payload;

public class AuthorizationMiddleware extends Middleware{
    @Override
    public boolean check(Payload payload) {
        return checkNext(payload);
    }
}
