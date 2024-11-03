import request from '@/utils/request'

export function getComponentPageList(data) {
    return request({
        url: '/Component/getComponentPageList',
        method: 'post',
        data
    })
}
export function DeleComponent(data) {
    return request({
        url: '/Component/changeComponentStatus',
        method: 'post',
        data
    })
} //resourceManagement
export function getComponentList() {
    return request({
        url: '/Component/getComponentList',
        method: 'post',

    })
} //resourceManagement
export function getComponentDetail(data) {
    return request({
        url: '/Component/getComponentDetail',
        method: 'post',
        data
    })
} //resourceManagement