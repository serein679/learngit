import request from '@/utils/request'


export function Select(data) {
  return request({
    url: '/Archive/Select',
    method: 'post',
    data
  })
}
export function getArchives(data) {
  return request({
    url: '/Archive/getArchiveList',
    method: 'post',
    data
  })
}

export function getArchiveLists(data) {
  return request({
    url: '/Archive/getArchiveLists',
    method: 'post',
    data
  })
}
export function getArchiveee(data) {
  return request({
    url: '/Archive/getArchiveee',
    method: 'post',
    data
  })
}
export function SelectArchive(data) {
  return request({
    url: '/Archive/SelectArchive',
    method: 'post',
    data
  })
}
export function getArchive(data) {
  return request({
    url: '/Archive/getArchive',
    method: 'post',
    data
  })
}
export function DeleArchive(data) {
  return request({
    url: '/Archive/changeArchiveStatus',
    method: 'post',
    data
  })
}
export function DeleArchive1(data) {
  return request({
    url: '/Archive/changeArchiveStatus',
    method: 'post',
    data
  })
}
export function getArchivePic(data) {
  return request({
    url: '/Archive/getArchivePic',
    method: 'post',
    data
  })
}
export function PageAllArchive(data) {
  return request({
    url: '/Archive/getArchivePageList',
    method: 'post',
    data
  })
}
export function getArchiveHomeList() {
  return request({
    url: '/Archive/getArchiveHomeList',
    method: 'post'
  })
}
export function PageAllArchiveq(data) {
  return request({
    url: '/Archive/PageAllArchiveq',
    method: 'post',
    data
  })
}
export function getOneArchive(data) {
  return request({
    url: '/Archive/getArchiveDetail',
    method: 'post',
    data
  })
}
export function getOneArchivee(data) {
  return request({
    url: '/Archive/getOneArchivee',
    method: 'post',
    data
  })
}
export function editArchive(data) {
  return request({
    url: '/Archive/editArchive',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement
export function addArchive(data) {
  return request({
    url: '/Archive/addArchive',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement
export function addArchivee(data) {
  return request({
    url: '/Archive/addArchivee',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement
export function getOneArchive1(data) {
  return request({
    url: '/Archive/getOneArchive1',
    method: 'post',
    data
  })
}
export function editRe(data) {
  return request({
    url: '/Archive/editRe',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    processData: false,
    data
  })
}//resourceManagement
export function UpdateStatus(data) {
  return request({
    url: '/Archive/updateStatus',
    method: 'post',

    data
  })
}

