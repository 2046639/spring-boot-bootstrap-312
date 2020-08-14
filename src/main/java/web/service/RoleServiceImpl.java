package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public Role getRoleByName(String name) {
        return this.roleDao.getRoleByName(name);
    }

    @Override
    public List<Role> getListRoles() {
        return this.roleDao.getListRoles();
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        this.roleDao.addRole(role);
    }


}
