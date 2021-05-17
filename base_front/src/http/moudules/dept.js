import axios from '../axios'


// 查询全部
export const findList = () => {
  return axios({
    url: '/dept/getList',
    method: 'get'
  })
}
