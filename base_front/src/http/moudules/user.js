import axios from '../axios'

/* 
 * 用户管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/user/save',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/user/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/user/findPage',
        method: 'post',
        data
    })
}
// 查找用户的菜单权限标识集合
export const findPermissions = (params) => {
    return axios({
        url: '/user/findPermissions',
        method: 'get',
        params
    })
}

// 查询图片
export const getImagePrePath = () => {
  return axios({
    url: '/api/basedata/getImagePrePath',
    method: 'get'
  })
}

//根据用户代码获取用户对象
export const findByDocCode = (params) => {
  return axios({
    url: '/user/findByName',
    method: 'get',
    params
  })
}

//修改密码
export const updatePassword = (params) => {
  return axios({
    url: '/user/updatePassword',
    method: 'get',
    params
  })
}

//获取病人数量
export const findPatNum = ()  => {
  return axios({
    url: '/api/queue/findPatNum',
    method: 'get'
  })
}
//获取医生数量
export const findWorkingDoctorNum = ()  => {
  return axios({
    url: '/doctoruse/findWorkingDoctorNum',
    method: 'get'
  })
}
//获取在线分诊屏对象
export const getOnTvUsers = ()  => {
  return axios({
    url: '/api/netty/getOnUsers',
    method: 'get'
  })
};
//根据ByRoleId查找对应的人员
export const findUserByIsFpUser = (params) => {
  return axios({
    url: '/user/findUserByIsFpUser',
    method: 'get',

  })
};
