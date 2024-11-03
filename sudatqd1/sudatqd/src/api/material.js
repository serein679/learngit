import request from '@/utils/request'



export function getPaperRawMaterials(data) {
    return request({
        url: '/paperRawMaterial/getPaperRawMaterials',
        method: 'post',
        data
    })
}
export function PageAllMaterial(data) {
    return request({
        url: '/Material/PageAllMaterial',
        method: 'post',
        data
    })
}