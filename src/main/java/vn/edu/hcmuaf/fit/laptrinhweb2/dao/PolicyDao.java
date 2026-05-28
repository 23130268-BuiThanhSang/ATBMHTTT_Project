package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Policy;

import java.util.List;

public class PolicyDao extends  BaseDao  {
    public List<Policy> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM Policy")
                        .mapToBean(Policy.class)
                        .list()
        );
    }
}
