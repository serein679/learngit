import request from '@/utils/request'

// 登录
// data:{useraccount:"xxx",password:"xxx"}
// 正确返回token字符串，错误返回useriderr，statuserr，acterr，pwderr
export function login(data) {
  return request({
    url: '/Login/loginResult',
    method: 'post',
    data
  })
}
export function checkQues(data) {
  return request({
    url: '/Login/checkQues',
    method: 'post',
    data
  })
}
export function resetPass(data) {
  return request({
    url: '/Login/resetPass',
    method: 'post',
    data
  })
}
export function logout(data) {
  return request({
    url: '/Login/LogOut',
    method: 'post',
    data
  })
}
export function enroll(data) {
  return request({
    url: '/Login/enroll',
    method: 'post',
    data
  })
}
export function getUserInfo() {
  return request({
    url: '/Login/GetUserInfo',
    method: 'post'
  })
}
export function uploadOcr(data) {
  return request({
    url: '/archive/ocr/uploadOcr',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    // processData: false,
    data
  })
}

