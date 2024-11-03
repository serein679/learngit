import request from '@/utils/request'

export function getHistoryList(data) {
    return request({
        url: '/HistoricalEvents/getHistoryList',
        method: 'post',
        data
    })
}
export function getOneHistoricalEvent(data) {
    return request({
        url: '/HistoricalEvents/getOneHistoricalEvent',
        method: 'post',
        data
    })
}
export function SelectHis(data) {
    return request({
        url: '/HistoricalEvents/SelectHis',
        method: 'post',
        data
    })
}
export function PageAllHis(data) {
    return request({
        url: '/HistoricalEvents/PageAllHis',
        method: 'post',
        data
    })
}
export function PageAllHiss(data) {
    return request({
        url: '/HistoricalEvents/PageAllHiss',
        method: 'post',
        data
    })
}
export function editEvent(data) {
    return request({
        url: '/HistoricalEvents/editEvent',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement
export function addEvent(data) {
    return request({
        url: '/HistoricalEvents/addEvent',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement
export function deleHis(data) {
    return request({
        url: '/HistoricalEvents/deleHis',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement
export function deleHis1(data) {
    return request({
        url: '/HistoricalEvents/deleHis1',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement