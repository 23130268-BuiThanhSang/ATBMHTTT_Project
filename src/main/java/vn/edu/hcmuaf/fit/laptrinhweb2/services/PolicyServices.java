package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.PolicyDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Policy;

import java.util.List;

public class PolicyServices {
    PolicyDao policyDao = new PolicyDao();
    public List<Policy> getAll() {
        return policyDao.getAll();
    }
}
