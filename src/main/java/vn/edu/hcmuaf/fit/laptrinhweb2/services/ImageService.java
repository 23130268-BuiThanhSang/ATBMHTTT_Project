package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ImageDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageService {
    private static final String ROOT = "D:/lgImg/";
    private final ImageDao imageDao = new ImageDao();
    public File getDirectory(String relativePath) {
        if (relativePath == null) {
            relativePath = "";
        }
        File dir = new File(ROOT, relativePath);

        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        return dir;
    }

    public File[] getAllFiles(String relativePath) {
        File dir = getDirectory(relativePath);
        if (dir == null) {
            return null;
        }
        return dir.listFiles();
    }
    public File getImageFile(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return null;
        }

        File file = new File(ROOT, relativePath);

        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    public boolean renameImage(String relativePath, String newName) {
        File oldFile = new File(ROOT, relativePath);

        if (!oldFile.exists() || !oldFile.isFile()) {
            return false;
        }

        String oldName = oldFile.getName();
        int dot = oldName.lastIndexOf(".");
        if (dot == -1) {
            return false;
        }

        String extension = oldName.substring(dot);
        File newFile = new File(oldFile.getParent(), newName + extension);

        if (newFile.exists()) {
            return false;
        }

        boolean ok = oldFile.renameTo(newFile);
        if (!ok) return false;

        Image image = imageDao.getByUrl(relativePath);
        if (image != null) {
            String parent = new File(relativePath).getParent();
            String newUrl = (parent == null ? "" : parent + "/")
                    + newName + extension;

            image.setImage_url(newUrl);
            imageDao.update(image);
        }

        return true;
    }


    public String uploadImage(String relativeDir, Part filePart) throws IOException {
        if (relativeDir == null) {
            relativeDir = "";
        }

        File dir = new File(ROOT, relativeDir);
        if (!dir.exists() || !dir.isDirectory()) {
            return "";
        }

        if (filePart == null || filePart.getSize() == 0) {
            return "";
        }

        String fileName = Path.of(filePart.getSubmittedFileName())
                .getFileName().toString();

        File dest = new File(dir, fileName);
        filePart.write(dest.getAbsolutePath());

        Image image = new Image();
        String image_url = "";
        if (relativeDir.equals("")) {
            image_url = fileName;
        } else {
            image_url = relativeDir + "/" + fileName;
        }
        image.setImage_url(image_url);

        return image_url;
    }

    public boolean deleteImage(String relativePath) {
        File file = new  File(ROOT, relativePath);

        if (!file.exists() || !file.isFile()) {
            return false;
        }

        boolean deleted = file.delete();
        if (!deleted) return false;
        System.out.println("DELETE IMAGE URL = [" + relativePath + "]");
        Image image = imageDao.getByUrl(relativePath);
        if (image != null) {
            imageDao.deleteById(image.getId());
        }

        return true;
    }

    public boolean moveImage(String fromPath, String toDirPath) {
        if (fromPath == null || toDirPath == null) {
            return false;
        }

        File source = new File(ROOT, fromPath);
        File targetDir = new File(ROOT, toDirPath);

        if (!source.exists() || !source.isFile()) {
            return false;
        }

        if (!targetDir.exists() || !targetDir.isDirectory()) {
            return false;
        }

        File targetFile = new File(targetDir, source.getName());

        // prevent overwrite
        if (targetFile.exists()) {
            return false;
        }

        boolean moved = source.renameTo(targetFile);
        if (!moved) return false;

        // ===== DB SYNC =====
        Image image = imageDao.getByUrl(fromPath);
        if (image != null) {
            String newUrl = toDirPath.isEmpty()
                    ? source.getName()
                    : toDirPath + "/" + source.getName();

            image.setImage_url(newUrl);
            imageDao.update(image);
        }

        return true;
    }


}
