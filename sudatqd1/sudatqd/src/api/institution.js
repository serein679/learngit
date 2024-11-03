import request from '@/utils/request'



export function getInstitutions(data) {
  return request({
    url: '/Institution/getInstitutionLists',
    method: 'post',
    data
  })
}

export function getOneInstitution(data) {
  return request({
    url: '/Institution/getInstitutionDetail',
    method: 'post',
    data
  })
}
export function PageAllInstitution(data) {
  return request({
    url: '/Institution/getInstitutionPageList',
    method: 'post',
    data
  })
}
export function PageAllInstitutionn(data) {
  return request({
    url: '/Institution/PageAllInstitutionn',
    method: 'post',
    data
  })
}
export function SelectInstitution(data) {
  return request({
    url: '/Institution/SelectInstitution',
    method: 'post',
    data
  })
}
export function getOneIn(data) {
  return request({
    url: '/Institution/getInstitutionDetail',
    method: 'post',
    data
  })
}
export function getOnePa(data) {
  return request({
    url: '/Institution/getOnePa',
    method: 'post',
    data
  })
}
export function deleInstitution(data) {
  return request({
    url: '/Institution/changeInstitutionStatus',
    method: 'post',
    // headers: {
    //   'Content-Type': 'multipart/form-data'
    // },
    data
  })
}


export function editInstitution(data) {
  return request({
    url: '/Institution/editInstitution',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement
export function addInstitution(data) {
  return request({
    url: '/Institution/addInstitution',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement