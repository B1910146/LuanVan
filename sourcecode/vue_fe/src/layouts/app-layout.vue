<template>
    <div>
        <Header></Header>

        <div class="main-container" id="container" :class="[!$store.state.is_show_sidebar ? 'sidebar-closed sbar-open' : '', $store.state.menu_style === 'collapsible-vertical' ? 'collapsible-vertical-mobile' : '']">
            <div class="overlay" :class="{ show: !$store.state.is_show_sidebar }" @click="$store.commit('toggleSideBar', !$store.state.is_show_sidebar)"></div>
            <div class="search-overlay" :class="{ show: $store.state.is_show_search }" @click="$store.commit('toggleSearch', !$store.state.is_show_search)"></div>

            <Sidebar></Sidebar>

            <div id="content" class="main-content">
                <router-view />

                <Footer></Footer>
            </div>

            <app-settings />
        </div>
    </div>
</template>

<script setup>
    import Header from "../components/layout/header.vue";
    import Sidebar from "../components/layout/sidebar.vue";
    import Footer from "../components/layout/footer.vue";
    import appSettings from "../components/app-settings.vue";
</script>
