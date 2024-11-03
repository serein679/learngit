<template>
    <el-container>
        <el-header style="padding: 0%;">
            <Header />
        </el-header>
        <el-main style="padding: 0%;height: auto;">
            <AppMain />
        </el-main>
        <el-footer style="padding: 0%;">
            <Bottom />
        </el-footer>
    </el-container>
</template>

<script>
import Header from './components/header2.vue'
import AppMain from './components/main.vue'
import { mapState } from 'vuex';
import Bottom from './components/bottom.vue';

export default {
    components: {
        Header,
        AppMain,
        Bottom
    },
    computed: {
        ...mapState({
        }),
        classObj() {
            return {
            }
        }
    },
}
</script>