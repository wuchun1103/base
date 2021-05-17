/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/**
 * Json对象是否为空
 */
export function isObjEmpty (obj) {
  if(obj==null || JSON.stringify(obj) == "{}" || obj=="")
    return true
  return false
}

/**
 * 字符串是否为空
 */
export function isStrEmpty(str){
  if(typeof str == "undefined" || str == null || str == "" || str === undefined){
    return true;
  }else{
    return false;
  }
}
/**
 * 判断对象是否为空
 * @param obj
 * @returns {boolean}
 */
export function isNotEmpty (obj){
  let flag = true
  if (obj === '' || obj == null || obj === undefined || obj === 'undefined') {
    flag = false
  }
  return flag
}
