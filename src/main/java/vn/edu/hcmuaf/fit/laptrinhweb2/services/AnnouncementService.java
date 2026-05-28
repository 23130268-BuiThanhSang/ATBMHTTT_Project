package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.AnnouncementDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.AccountStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Announcement;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementService {

    private final AnnouncementDao announcementDao = new AnnouncementDao();

    public void addAnnouncement(
            AccountStatus accountStatus,
            String content,
            String targetUrl,
            Integer expDay,
            Integer expMonth,
            Integer expYear
    ) {

        Announcement announcement = new Announcement();

        announcement.setAccountStatus(accountStatus);

        announcement.setContent(content);
        announcement.setTargetUrl(
                (targetUrl == null || targetUrl.isBlank()) ? null : targetUrl
        );

        if (expDay != null && expMonth != null && expYear != null) {
            announcement.setExpiresAt(
                    LocalDateTime.of(expYear, expMonth, expDay, 23, 59, 59)
            );
        } else {
            announcement.setExpiresAt(null);
        }

        announcement.setActive(true);

        announcementDao.insert(announcement);
    }
    public List<Announcement> getAllAnnouncements() {
        return announcementDao.getAll();
    }

    public void deleteAnnouncement(int id) {
        announcementDao.delete(id);
    }
    public void updateAnnouncement(Announcement announcement) {
        announcementDao.update(announcement);
    }
}
