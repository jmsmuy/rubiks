package com.jmsmuy;

import java.lang.reflect.Method;

public enum ValidMoves {
    Un("doUn"),
    Ur("doUr"),
    Dn("doDn"),
    Dr("doDr"),
    Fn("doFn"),
    Fr("doFr"),
    Bn("doBn"),
    Br("doBr"),
    Rn("doRn"),
    Rr("doRr"),
    Ln("doLn"),
    Lr("doLr");

    private final String methodName;
    private Method method;

    ValidMoves(String methodName) {
        this.methodName = methodName;
        try {
            this.method = Cube.class.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            System.out.println("Error getting method on enum creation for " + methodName);
            this.method = null;
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }
}
