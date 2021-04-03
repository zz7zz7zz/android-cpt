package debug;

import android.app.Application;

import com.module.init.Initializer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.initArouter(this);
    }
}
