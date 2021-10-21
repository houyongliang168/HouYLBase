package mvvm;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.concurrent.Callable;

public class ProjectViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<?>, Callable<? extends ViewModel>> creators;
    public ProjectViewModelFactory(Map<Class<?>, Callable<? extends ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        {
            Callable<? extends ViewModel> creator = creators.get(modelClass);
            if (creator == null) {
                for (Map.Entry<Class<?>, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                    if (modelClass.isAssignableFrom(entry.getKey())) {
                        creator = entry.getValue();
                        break;
                    }
                }
            }
            if (creator == null) {
                throw new IllegalArgumentException("Unknown model class " + modelClass);
            }
            try {
                return (T) creator.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
