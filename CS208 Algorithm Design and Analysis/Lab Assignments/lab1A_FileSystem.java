import java.io.*;
import java.util.*;

public class lab1A_FileSystem {
    private static final Directory root = new Directory(".");
    public static void main(String[] args) {
        QReader in = new QReader();

        int n = in.nextInt(), m = in.nextInt();

        for (int i = 0; i < n + m; i++) {
            String command = in.nextLine();
            String[] arguments = command.split(" ");
            switch (arguments[0]) {
                case "echo":
                    echo(arguments.length > 3 ? arguments[1] : "", arguments[arguments.length - 1]);
                    break;
                case "mkdir":
                    mkdir(arguments[1]);
                    break;
                case "rm":
                    rm(arguments.length > 2 ? arguments[1] : "", arguments[arguments.length - 1]);
                    break;
                case "mv":
                    mv(arguments[1], arguments[2]);
                    break;
                case "cat":
                    cat(arguments[1]);
                    break;
                case "find":
                    if (arguments.length == 1) { find(".", ""); }  // only "find"
                    else if (arguments.length == 2) { find(arguments[1], ""); }  // only "find [path]"
                    else {
                        StringBuilder sb = new StringBuilder();
                        if (arguments[1].startsWith("-")) {
                            for (int j = 1; j < arguments.length; j++) {
                                sb.append(arguments[j]).append(" ");
                            }
                            find(".", sb.toString().trim());
                        } else {
                            for (int j = 2; j < arguments.length; j++) {
                                sb.append(arguments[j]).append(" ");
                            }
                            find(arguments[1], sb.toString().trim());
                        }
                    }
                    break;
            }
        }

    }

    private static void echo(String c, String path) {
        int index = path.lastIndexOf('/');
        Directory father = index == -1 ? root : resolveDirPath(path.substring(0, index));
        String fileName = index == -1 ? path : path.substring(index + 1);
        File f = new File(fileName, c + "\n");
        father.addSubFile(f);
    }

    private static void mkdir(String path) {
        Directory cur = resolveDirPath(path);
        String dirName = path.substring(path.lastIndexOf('/') + 1);
        cur.addSubDirectory(new Directory(dirName));
    }

    private static void rm(String op, String path) {
        switch (op) {
            case "":
                File f = resolveFilePath(path);
                f.getFather().removeSubFile(f);
                break;
            case "-rf":
                Directory d = resolveDirPath(path);
                //deleteIterator(d);  // ??????
                d.getFather().removeSubDir(d);
        }
    }

    private static void deleteIterator(Directory d) {
        for (Directory dirs : d.subDirs.values()) { deleteIterator(dirs); }
        d.removeAll();
    }

    private static void mv(String srcPath, String dstPath) {
        File srcFile = resolveFilePath(srcPath);
        Directory srcDir = resolveDirPath(srcPath);
        boolean isFile = srcFile != null;
        Directory dst = resolveDirPath(dstPath);

        if (isFile) {
            srcFile.getFather().removeSubFile(srcFile);
            dst.addSubFile(srcFile);
        }
        else {
            srcDir.getFather().removeSubDir(srcDir);
            dst.addSubDirectory(srcDir);
        }
    }

    private static void cat(String filePath) {
        File f = resolveFilePath(filePath);
        System.out.print(f.getContent());
    }

    private static void find(String path, String expr) {
        Directory src = resolveDirPath(path);
        String type = null;
        String name = null;
        if (expr != null && !expr.trim().isEmpty()) {
            String[] tokens = expr.trim().split("\\s+");
            for (int i = 0; i < tokens.length; i++) {
                if ("-type".equals(tokens[i])) {
                    if (i + 1 < tokens.length) {
                        type = tokens[i + 1];
                        i++;
                    }
                } else if ("-name".equals(tokens[i])) {
                    if (i + 1 < tokens.length) {
                        name = tokens[i + 1];
                        i++;
                    }
                }
            }
        }

        ArrayList<String> results = new ArrayList<>();
        findIterator(src, path, name, type, results);

        System.out.println(results.size());
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static void findIterator(Object cur, String curPath, String name, String type, ArrayList<String> results) {
        boolean matches = true;
        if (type != null) {  // no "-type" argument
            if ("f".equals(type) && !(cur instanceof File)) {
                matches = false;
            } else if ("d".equals(type) && !(cur instanceof Directory)) {
                matches = false;
            }
        }
        if (name != null) {  // no "-name" argument
            String curName = null;
            if (cur instanceof Directory) {
                curName = ((Directory) cur).getName();
            } else if (cur instanceof File) {
                curName = ((File) cur).getName();
            }
            if (curName == null || !curName.equals(name)) {
                matches = false;
            }
        }
        if (matches) {
            results.add(curPath.endsWith("/") ? curPath.substring(0, curPath.length() - 1) : curPath);
        }

        if (cur instanceof Directory curDir) {
            for (Directory subDir : curDir.subDirs.values()) {
                String subPath = curPath + (curPath.endsWith("/") ? "" : "/") + subDir.getName();
                findIterator(subDir, subPath, name, type, results);
            }
            for (File file : curDir.subFiles.values()) {
                String filePath = curPath + (curPath.endsWith("/") ? "" : "/") + file.getName();
                findIterator(file, filePath, name, type, results);
            }
        }
    }

    private static Directory resolveDirPath(String path) {
        Directory cur = root;
        String[] paths = path.split("/");
        for (String p : paths) {
            if (p.equals(".") || p.isEmpty()) {
                cur = cur;
            }
            else if (p.equals("..")) {
                cur = cur.father;
            }
            else {
                if (cur.subDirs.get(p) == null) return cur;
                else cur = cur.subDirs.get(p);
            }
        }
        return cur;
    }

    private static File resolveFilePath(String path) {
        Directory cur = root;
        String[] paths = path.split("/");
        for (int i = 0; i < paths.length - 1; i++) {
            String p = paths[i];
            if (p.equals(".")) {
                cur = cur;
            }
            else if (p.equals("..")) {
                cur = cur.father;
            }
            else {
                cur = cur.subDirs.get(p);
            }
        }
        return cur.subFiles.get(paths[paths.length - 1]);
    }

    private static class File {
        String name;
        String content;
        Directory father;

        private File(String n, String c) {
            this.name = n;
            this.content = c;
            this.father = null;
        }

        public String getName() { return name; }

        public String getContent() { return content; }

        public Directory getFather() { return father; }
    }

    private static class Directory {
        String name;
        Directory father;
        HashMap<String, Directory> subDirs;
        HashMap<String, File> subFiles;

        private Directory(String n) {
            this.name = n;
            this.father = null;
            this.subFiles = new HashMap<>();
            this.subDirs = new HashMap<>();
        }

        private void addSubFile(File f) {
            this.subFiles.put(f.name, f);
            f.father = this;
        }

        private void addSubDirectory(Directory d) {
            this.subDirs.put(d.name, d);
            d.father = this;
        }

        private void removeAll() {
            Collection<File> files = subFiles.values();
            Collection<Directory> dirs = subDirs.values();
            for (File file : files) { removeSubFile(file); }
            for (Directory dir : dirs) { removeSubDir(dir); }
            this.subDirs.clear();
            this.subFiles.clear();
        }

        private void removeSubFile(File f) {
            subFiles.remove(f.getName(), f);
            f.father = null;
        }

        private void removeSubDir(Directory d) {
            subDirs.remove(d.getName(), d);
            d.father = null;
        }

        public String getName() { return name; }

        public Directory getFather() { return father; }
    }

    private static class QReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
