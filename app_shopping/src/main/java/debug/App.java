package debug;

import com.module.BaseApplication;
import com.module.Initializer;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.init(this);
    }
}
