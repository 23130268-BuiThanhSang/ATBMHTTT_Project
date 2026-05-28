package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.BannerDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;

import java.util.List;

public class BannerService {

    private final BannerDao bannerDao = new BannerDao();

    public void addBanner(Banner banner) {
        bannerDao.addBanner(banner);
    }

    public List<Banner> getAllBanners() {
        return bannerDao.getAll();
    }

    public Banner getById(int id) {
        return bannerDao.getById(id);
    }

    public List<Banner> getTop5BannersActive(){
        return bannerDao.getTop5ActiveBanners();
    }
    public boolean update(Banner banner) {
        return bannerDao.update(banner);
    }

    public boolean delete(int id) {
        return bannerDao.deleteById(id);
    }
}
