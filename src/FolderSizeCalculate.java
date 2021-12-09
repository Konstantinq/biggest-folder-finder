import org.w3c.dom.Node;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculate  extends RecursiveTask<Long> {
        private final File folder;

        public FolderSizeCalculate(File folder) {
            this.folder = folder;
        }

        @Override
        protected Long compute() {
            if(folder.isFile()){
                return folder.length();
            }

            long sum = 0;

            List<FolderSizeCalculate> subTasks = new LinkedList<>();
            File[] files = folder.listFiles();
            for (File file : files) {
                FolderSizeCalculate task = new FolderSizeCalculate(file);
                task.fork(); // запустим асинхронно
                subTasks.add(task);
            }

            for (FolderSizeCalculate task : subTasks) {
                sum += task.join(); // дождёмся выполнения задачи и прибавим результат
            }

            return sum;
        }

}
