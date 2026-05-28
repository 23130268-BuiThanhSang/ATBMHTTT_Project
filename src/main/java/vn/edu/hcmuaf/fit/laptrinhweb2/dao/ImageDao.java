package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Image;

import java.util.List;

public class ImageDao extends BaseDao{
    public List<Image> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM image")
                        .mapToBean(Image.class)
                        .list()
        );
    }

    public Image getById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM image WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Image.class)
                        .findOne()
                        .orElse(null)
        );
    }
    public Image getByUrl(String imageUrl) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM image WHERE image_url = :url")
                        .bind("url", imageUrl)
                        .mapToBean(Image.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public int insert(Image image) {
        return get().withHandle(h ->
                h.createUpdate("""
                INSERT INTO image (image_url)
                VALUES (:url)
            """)
                        .bind("url", image.getImage_url())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }


    public void update(Image image) {
        get().useHandle(h ->
                h.createUpdate("""
                    UPDATE image
                    SET image_url = :image_url
                    WHERE id = :id
                """)
                        .bindBean(image)
                        .execute()
        );
    }

    public void deleteById(int id) {
        get().useHandle(h ->
                h.createUpdate("DELETE FROM image WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }
}
