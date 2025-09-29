package DocumentManagementSystem.middlewares;

import DocumentManagementSystem.Payload;

public class AuthenticationMiddleware extends Middleware{
    @Override
    public boolean check(Payload payload) {
        return checkNext(payload);
    }
}
