package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductGroupDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;

import java.util.List;

public class ProductGroupService {

    private final ProductGroupDao productGroupDao = new ProductGroupDao();

    public List<ProductGroup> getGroups(GroupType type) {
        if (type == null) {
            throw new IllegalArgumentException("GroupType cannot be null");
        }
        //        for (ProductGroup group : gl) {
//            System.out.println(group.getImageUrl());
//            System.out.println(group.getThumbnailUrl());
//        }
        return productGroupDao.getGroups(type);
    }

    public ProductGroup getGroup(GroupType type, int id) {
        if (type == null) {
            throw new IllegalArgumentException("GroupType cannot be null");
        }
        return productGroupDao.getProductGroup(type, id);
    }

    public void addGroup(
            GroupType groupType,
            String groupName,
            String thumbnailUrl,
            String imageUrl,
            Integer displayOrder
    ) {
        if (groupType == null) {
            throw new IllegalArgumentException("Invalid group type");
        }

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name is required");
        }

        int finalDisplayOrder = (displayOrder == null || displayOrder < 0)
                ? 1
                : displayOrder;

        productGroupDao.addGroup(
                groupType,
                groupName.trim(),
                thumbnailUrl,
                imageUrl,
                finalDisplayOrder
        );
    }

    public void updateGroup(
            GroupType groupType,
            int id,
            String groupName,
            String thumbnailUrl,
            String imageUrl,
            Integer displayOrder
    ) {
        if (groupType == null) {
            throw new IllegalArgumentException("Invalid group type");
        }

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name is required");
        }

        int finalDisplayOrder = (displayOrder == null || displayOrder < 0)
                ? 1
                : displayOrder;

        productGroupDao.updateGroup(
                groupType,
                id,
                groupName.trim(),
                thumbnailUrl,
                imageUrl,
                finalDisplayOrder
        );
    }

    public void deleteGroup(GroupType groupType, int id) {
        if (groupType == null) {
            throw new IllegalArgumentException("Invalid group type");
        }

        productGroupDao.deleteGroup(groupType, id);
    }
    /**
     * Lấy danh sách nhóm sản phẩm hàng đầu theo loại nhóm lấy 10 collection hoặc 12 brand cso display order thấp nhất
     * @param type
     * @return
     */
    public List<ProductGroup> getTopProductGroupsByType(GroupType type) {
       if (type == null) {
           throw new IllegalArgumentException("GroupType cannot be null");
       }
       return productGroupDao.getTopProductGroups(type);
    }
}
