import request from '@/utils/request'

export function GetUsers() {
  return request({
    url: '/User/getUsers',
    method: 'post'
  })
}
export function SelectUser(data) {
  return request({
    url: '/User/SelectUser',
    method: 'post',
    data
  })
}
export function SelectUser1(data) {
  return request({
    url: '/User/SelectUser1',
    method: 'post',
    data
  })
}
export function SelectUser2(data) {
  return request({
    url: '/User/SelectUser2',
    method: 'post',
    data
  })
}
export function getOneUser(data) {
  return request({
    url: '/User/getUserDetail',
    method: 'post',
    data
  })
}
export function getOneUser1(data) {
  return request({
    url: '/User/getOneUser1',
    method: 'post',
    data
  })
}


export function UserRoles(data) {
  return request({
    url: '/users/userRoles',
    method: 'post',
    data
  })
}
export function getUs(data) {
  return request({
    url: '/User/getUs',
    method: 'post',
    data
  })
}


export function UpdateStatus(data) {
  return request({
    url: '/User/changeUserStatus',
    method: 'post',
    data
  })
}



export function ChooseOneUser(data) {
  return request({
    url: '/User/selectOneUser',
    method: 'post',
    data
  })
}

export function editUser(data) {
  return request({
    url: '/PersonalCenter/editUser',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}

export function addUser(data) {
  return request({
    url: '/Login/register',
    method: 'post',
    // headers: {
    //   'Content-Type': 'multipart/form-data'
    // },
    processData: false,
    data
  })
}
export function addUser1(data) {
  return request({
    url: '/User/addUser1',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}

export function DeleUser(data) {
  return request({
    url: '/User/deletUser',
    method: 'post',
    data
  })
}
export function PageAllUser(data) {
  return request({
    url: '/User/getUserPageList',
    method: 'post',
    data
  })
}