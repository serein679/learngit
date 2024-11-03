import request from '@/utils/request'



export function getPaperTime(data) {
  return request({
    url: '/Paper/getPaperTime',
    method: 'post',
    data
  })
}
export function getPaper(data) {
  return request({
    url: '/Paper/getPaperList',
    method: 'post',
    data
  })
}
export function getPaperss(data) {
  return request({
    url: '/Paper/getPaperLists',
    method: 'post',
    data
  })
}
export function getPapers(data) {
  return request({
    url: '/Paper/getPapers',
    method: 'post',
    data
  })
}
 
export function getPaperVo(data) {
  return request({
    url: '/Paper/getPaperVo',
    method: 'post',
    data
  })
}
export function SelectPaper(data) {
  return request({
    url: '/Paper/SelectPaper',
    method: 'post',
    data
  })
}
export function getOnePaper(data) {
  return request({
    url: '/Paper/getPaperDetail',
    method: 'post',
    data
  })
}

export function DelePaper(data) {
  return request({
    url: '/Paper/changePaperStatus',
    method: 'post',
    // headers: {
    //   'Content-Type': 'multipart/form-data'
    // },
    data
  })
}
export function PageAllPaper(data) {
  return request({
    url: '/Paper/getPaperPageList',
    method: 'post',
    data
  })
}
export function PageAllPaperr(data) {
  return request({
    url: '/Paper/PageAllPaperr',
    method: 'post',
    data
  })
}
export function editPaper(data) {
  return request({
    url: '/Paper/editPaper',
    method: 'post',
    processData: false,
    data
  })
}//resourceManagement
export function addPaper(data) {
  return request({
    url: '/Paper/addPaper',
    method: 'post',
    data
  })
}//r