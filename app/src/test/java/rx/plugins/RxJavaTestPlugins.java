package rx.plugins;

/**
 * Created by kapilbakshi on 17/08/17.
 */

public class RxJavaTestPlugins extends RxJavaPlugins {
    RxJavaTestPlugins() {
        super();
    }

    public static void resetPlugins(){
        getInstance().reset();
    }
}