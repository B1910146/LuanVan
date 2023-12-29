<template>
    <div>
        <div class="header-container fixed-top">
            <header class="header navbar navbar-expand-sm">
                <ul class="navbar-item theme-brand flex-row text-center">
                    <li class="nav-item theme-logo">
                        <router-link to="/home">
                            <img src='/assets/images/SERPENT__1_-removebg-preview.png' class="navbar-logo" alt="logo" />
                        </router-link>
                    </li>
                    <li class="nav-item theme-text">
                        <router-link to="/home" class="nav-link"> SERPENT RESTAURANT </router-link>
                    </li>
                </ul>
                <div class="d-none horizontal-menu">
                    <a href="javascript:void(0);" class="sidebarCollapse" data-placement="bottom" @click="$store.commit('toggleSideBar', !$store.state.is_show_sidebar)">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="24"
                            height="24"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="currentColor"
                            stroke-width="2"
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            class="feather feather-menu"
                        >
                            <line x1="3" y1="12" x2="21" y2="12"></line>
                            <line x1="3" y1="6" x2="21" y2="6"></line>
                            <line x1="3" y1="18" x2="21" y2="18"></line>
                        </svg>
                    </a>
                </div>
                

                <div class="navbar-item flex-row ms-md-auto">
                    <div class="dark-mode d-flex align-items-center">
                        <a v-if="$store.state.dark_mode == 'light'" href="javascript:;" class="d-flex align-items-center" @click="toggleMode('dark')">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="20"
                                height="20"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-sun"
                            >
                                <circle cx="12" cy="12" r="5"></circle>
                                <line x1="12" y1="1" x2="12" y2="3"></line>
                                <line x1="12" y1="21" x2="12" y2="23"></line>
                                <line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line>
                                <line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line>
                                <line x1="1" y1="12" x2="3" y2="12"></line>
                                <line x1="21" y1="12" x2="23" y2="12"></line>
                                <line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line>
                                <line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line>
                            </svg>
                            <span class="ms-2">Light</span>
                        </a>
                        <a v-if="$store.state.dark_mode == 'dark'" href="javascript:;" class="d-flex align-items-center" @click="toggleMode('system')">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="20"
                                height="20"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-moon"
                            >
                                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path>
                            </svg>
                            <span class="ms-2">Dark</span>
                        </a>
                        <a v-if="$store.state.dark_mode == 'system'" href="javascript:;" class="d-flex align-items-center" @click="toggleMode('light')">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="20"
                                height="20"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-airplay"
                            >
                                <path d="M5 17H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2h-1"></path>
                                <polygon points="12 15 17 21 7 21 12 15"></polygon>
                            </svg>
                            <span class="ms-2">System</span>
                        </a>
                    </div>

                    <div class="dropdown nav-item user-profile-dropdown btn-group">
                        <a href="javascript:;" id="ddluser" data-bs-toggle="dropdown" aria-expanded="false" class="btn dropdown-toggle btn-icon-only user nav-link">
                            <span>{{ currentUser }}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="ddluser">
                            <li role="presentation">
                                <div>
                                    <a @click="openPopup" style='color: white' class='m-3 font-weight-bold'>
                                        Đổi mật khẩu
                                    </a>
                                </div>
                            </li>
                            
                            
                            <li role="presentation">
                                <a @click='handleLogout' class='m-3'>
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-log-out"
                                    >
                                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                                        <polyline points="16 17 21 12 16 7"></polyline>
                                        <line x1="21" y1="12" x2="9" y2="12"></line>
                                    </svg>
                                    Sign Out
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
        </div>
        <div class="sub-header-container">
            <header class="header navbar navbar-expand-sm">
                <a href="javascript:void(0);" class="sidebarCollapse" data-placement="bottom" @click="$store.commit('toggleSideBar', !$store.state.is_show_sidebar)">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="24"
                        height="24"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        class="feather feather-menu"
                    >
                        <line x1="3" y1="12" x2="21" y2="12"></line>
                        <line x1="3" y1="6" x2="21" y2="6"></line>
                        <line x1="3" y1="18" x2="21" y2="18"></line>
                    </svg>
                </a>

                <div id="breadcrumb" class="vue-portal-target"></div>
            </header>
        </div>
        <div class="topbar-nav header navbar" role="banner">
            <nav class="topbar">
                <ul class="list-unstyled menu-categories" id="topAccordion">
                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle autodroprown">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-home"
                                >
                                    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                    <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                </svg>
                                <span>{{ $t('dashboard') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li>
                                <router-link to="/home"> {{ $t('sales') }} </router-link>
                            </li>
                            <li>
                                <router-link to="/index2">{{ $t('analytics') }}</router-link>
                            </li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-cpu"
                                >
                                    <rect x="4" y="4" width="16" height="16" rx="2" ry="2"></rect>
                                    <rect x="9" y="9" width="6" height="6"></rect>
                                    <line x1="9" y1="1" x2="9" y2="4"></line>
                                    <line x1="15" y1="1" x2="15" y2="4"></line>
                                    <line x1="9" y1="20" x2="9" y2="23"></line>
                                    <line x1="15" y1="20" x2="15" y2="23"></line>
                                    <line x1="20" y1="9" x2="23" y2="9"></line>
                                    <line x1="20" y1="14" x2="23" y2="14"></line>
                                    <line x1="1" y1="9" x2="4" y2="9"></line>
                                    <line x1="1" y1="14" x2="4" y2="14"></line>
                                </svg>
                                <span>{{ $t('apps') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/apps/chat">Chat</router-link></li>
                            <li><router-link to="/apps/mailbox">Mailbox</router-link></li>
                            <li><router-link to="/apps/todo-list">Todo List</router-link></li>
                            <li><router-link to="/apps/notes">Notes</router-link></li>
                            <li><router-link to="/apps/scrumboard">Scrumboard</router-link></li>
                            <li><router-link to="/apps/contacts">Contacts</router-link></li>

                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;">
                                    Invoice
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><router-link to="/apps/invoice/list">List</router-link></li>
                                    <li><router-link to="/apps/invoice/preview">Preview</router-link></li>
                                    <li><router-link to="/apps/invoice/add">Add</router-link></li>
                                    <li><router-link to="/apps/invoice/edit">Edit</router-link></li>
                                </ul>
                            </li>
                            <li><router-link to="/apps/calendar">Calendar</router-link></li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-box"
                                >
                                    <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path>
                                    <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                                    <line x1="12" y1="22.08" x2="12" y2="12"></line>
                                </svg>
                                <span>{{ $t('components') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/components/tabs">Tabs</router-link></li>
                            <li><router-link to="/components/accordions">Accordions</router-link></li>
                            <li><router-link to="/components/modals">Modals</router-link></li>
                            <li><router-link to="/components/cards">Cards</router-link></li>
                            <li><router-link to="/components/carousel">Carousel</router-link></li>
                            <li><router-link to="/components/countdown">Countdown</router-link></li>
                            <li><router-link to="/components/counter">Counter</router-link></li>
                            <li><router-link to="/components/sweetalert">Sweet Alerts</router-link></li>
                            <li><router-link to="/components/timeline">Timeline</router-link></li>
                            <li><router-link to="/components/notifications">Notifications</router-link></li>
                            <li><router-link to="/components/media-object">Media Object</router-link></li>
                            <li><router-link to="/components/list-group">List Group</router-link></li>
                            <li><router-link to="/components/pricing-table">Pricing Tables</router-link></li>
                            <li><router-link to="/components/lightbox"> Lightbox </router-link></li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-zap"
                                >
                                    <polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>
                                </svg>
                                <span>{{ $t('ui_kit') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/elements/alerts">Alerts</router-link></li>
                            <li><router-link to="/elements/avatar">Avatar</router-link></li>
                            <li><router-link to="/elements/badges">Badges</router-link></li>
                            <li><router-link to="/elements/breadcrumbs">Breadcrumbs</router-link></li>
                            <li><router-link to="/elements/buttons">Buttons</router-link></li>
                            <li><router-link to="/elements/buttons-group">Button Groups</router-link></li>
                            <li><router-link to="/elements/color-library">Color Library</router-link></li>
                            <li><router-link to="/elements/dropdown">Dropdown</router-link></li>
                            <li><router-link to="/elements/infobox">Infobox</router-link></li>
                            <li><router-link to="/elements/jumbotron">Jumbotron</router-link></li>
                            <li><router-link to="/elements/loader">Loader</router-link></li>
                            <li><router-link to="/elements/pagination">Pagination</router-link></li>
                            <li><router-link to="/elements/popovers">Popovers</router-link></li>
                            <li><router-link to="/elements/progress-bar">Progress Bar</router-link></li>
                            <li><router-link to="/elements/search">Search</router-link></li>
                            <li><router-link to="/elements/tooltips">Tooltips</router-link></li>
                            <li><router-link to="/elements/treeview">Treeview</router-link></li>
                            <li><router-link to="/elements/typography">Typography</router-link></li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-layout"
                                >
                                    <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                                    <line x1="3" y1="9" x2="21" y2="9"></line>
                                    <line x1="9" y1="21" x2="9" y2="9"></line>
                                </svg>
                                <span>{{ $t('tables') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/tables/basic">Basic</router-link></li>
                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    DataTables
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li class="sub-sub-sub-submenu-list">
                                        <a href="javascript:;" class="dropdown-toggle">
                                            vue3-datatable
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="24"
                                                height="24"
                                                viewBox="0 0 24 24"
                                                fill="none"
                                                stroke="currentColor"
                                                stroke-width="2"
                                                stroke-linecap="round"
                                                stroke-linejoin="round"
                                                class="feather feather-chevron-right"
                                            >
                                                <polyline points="9 18 15 12 9 6"></polyline>
                                            </svg>
                                        </a>
                                        <ul class="collapse list-unstyled sub-sub-submenu">
                                            <li><router-link to="/tables/vue3-datatable/basic">Basic</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/advance">Advanced</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/order-sorting">Order Sorting</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/alt-pagination">Alt. Pagination</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/search">Search</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/checkbox">Checkbox</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/slot">Slot</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/column-filter">Column Filter</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/actions">Actions</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/sticky-header">Sticky Header</router-link></li>
                                            <li><router-link to="/tables/vue3-datatable/column-chooser">Column Chooser</router-link></li>
                                        </ul>
                                    </li>
                                    <li class="sub-sub-sub-submenu-list">
                                        <a href="javascript:;" class="dropdown-toggle">
                                            v3-table
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="24"
                                                height="24"
                                                viewBox="0 0 24 24"
                                                fill="none"
                                                stroke="currentColor"
                                                stroke-width="2"
                                                stroke-linecap="round"
                                                stroke-linejoin="round"
                                                class="feather feather-chevron-right"
                                            >
                                                <polyline points="9 18 15 12 9 6"></polyline>
                                            </svg>
                                        </a>
                                        <ul class="collapse list-unstyled sub-sub-submenu">
                                            <li><router-link to="/tables/v3-table/basic">Basic</router-link></li>
                                            <li><router-link to="/tables/v3-table/striped">Striped Table</router-link></li>
                                            <li><router-link to="/tables/v3-table/order-sorting">Order Sorting</router-link></li>
                                            <li><router-link to="/tables/v3-table/multi-column">Multi Column</router-link></li>
                                            <li><router-link to="/tables/v3-table/multiple-tables">Multiple Tables</router-link></li>
                                            <li><router-link to="/tables/v3-table/alt-pagination">Alt. Pagination</router-link></li>
                                            <li><router-link to="/tables/v3-table/custom">Custom</router-link></li>
                                            <li><router-link to="/tables/v3-table/range-search">Range Search</router-link></li>
                                            <li><router-link to="/tables/v3-table/export">Export</router-link></li>
                                            <li><router-link to="/tables/v3-table/live-dom-ordering">Live DOM ordering</router-link></li>
                                            <li><router-link to="/tables/v3-table/miscellaneous">Miscellaneous</router-link></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-clipboard"
                                >
                                    <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path>
                                    <rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect>
                                </svg>
                                <span>{{ $t('forms') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/forms/basic">Basic</router-link></li>
                            <li><router-link to="/forms/input-group">Input Group</router-link></li>
                            <li><router-link to="/forms/layouts">Layouts</router-link></li>
                            <li><router-link to="/forms/validation">Validation</router-link></li>
                            <li><router-link to="/forms/input-mask">Input Mask</router-link></li>
                            <li><router-link to="/forms/select2">Select2</router-link></li>
                            <li><router-link to="/forms/touchspin">TouchSpin</router-link></li>
                            <li><router-link to="/forms/checkbox-radio">Checkbox &amp; Radio</router-link></li>
                            <li><router-link to="/forms/switches">Switches</router-link></li>
                            <li><router-link to="/forms/wizards">Wizards</router-link></li>
                            <li><router-link to="/forms/file-upload">File Upload</router-link></li>
                            <li><router-link to="/forms/quill-editor">Quill Editor</router-link></li>
                            <li><router-link to="/forms/markdown-editor">Markdown Editor</router-link></li>
                            <li><router-link to="/forms/date-picker">Date &amp; Range Picker </router-link></li>
                            <li><router-link to="/forms/clipboard">Clipboard</router-link></li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-file"
                                >
                                    <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path>
                                    <polyline points="13 2 13 9 20 9"></polyline>
                                </svg>
                                <span>{{ $t('pages') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/pages/helpdesk">Helpdesk</router-link></li>
                            <li><router-link to="/pages/contact-us">Contact Form</router-link></li>
                            <li><router-link to="/pages/faq">FAQ</router-link></li>
                            <li><router-link to="/pages/faq2">FAQ 2</router-link></li>
                            <li><router-link to="/pages/privacy-policy">Privacy Policy</router-link></li>
                            <li><a target="_blank" href="/pages/coming-soon">Coming Soon</a></li>
                            <li><router-link to="/pages/blank-page">Blank Page</router-link></li>
                            <li><router-link to="/pages/sample">Sample Page</router-link></li>

                            <li><router-link to="/users/profile">Profile</router-link></li>
                            <li><router-link to="/users/account-setting">Account Settings</router-link></li>
                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    Error
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><a target="_blank" href="/pages/error404">404</a></li>
                                    <li><a target="_blank" href="/pages/error500">500</a></li>
                                    <li><a target="_blank" href="/pages/error503">503</a></li>
                                    <li><a target="_blank" href="/pages/maintenence">Maintanence</a></li>
                                </ul>
                            </li>
                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    Login
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><a target="_blank" href="/auth/login">Login Cover</a></li>
                                    <li><a target="_blank" href="/auth/login-boxed">Login Boxed</a></li>
                                </ul>
                            </li>
                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    Register
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><a target="_blank" href="/auth/register">Register Cover</a></li>
                                    <li><a target="_blank" href="/auth/register-boxed">Register Boxed</a></li>
                                </ul>
                            </li>

                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    Password Recovery
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><a target="_blank" href="/auth/pass-recovery">Recover ID Cover</a></li>
                                    <li><a target="_blank" href="/auth/pass-recovery-boxed">Recover ID Boxed</a></li>
                                </ul>
                            </li>

                            <li class="sub-sub-submenu-list">
                                <a href="javascript:;" class="dropdown-toggle">
                                    Lockscreen
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="24"
                                        height="24"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        stroke="currentColor"
                                        stroke-width="2"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        class="feather feather-chevron-right"
                                    >
                                        <polyline points="9 18 15 12 9 6"></polyline>
                                    </svg>
                                </a>
                                <ul class="collapse list-unstyled sub-submenu">
                                    <li><a target="_blank" href="/auth/lockscreen">Unlock Cover</a></li>
                                    <li><a target="_blank" href="/auth/lockscreen-boxed">Unlock Boxed</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <li class="menu single-menu">
                        <a href="javascript:;" class="dropdown-toggle">
                            <div class="">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="24"
                                    height="24"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    stroke-width="2"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="feather feather-plus-circle"
                                >
                                    <circle cx="12" cy="12" r="10"></circle>
                                    <line x1="12" y1="8" x2="12" y2="16"></line>
                                    <line x1="8" y1="12" x2="16" y2="12"></line>
                                </svg>
                                <span>{{ $t('more') }}</span>
                            </div>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="24"
                                height="24"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                stroke-width="2"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                class="feather feather-chevron-down"
                            >
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </a>
                        <ul class="collapse submenu list-unstyled">
                            <li><router-link to="/dragndrop">Drag and Drop</router-link></li>
                            <li><router-link to="/widgets">Widgets</router-link></li>
                            <li><router-link to="/charts/apex-chart">Charts</router-link></li>
                            <li><router-link to="/font-icons">Font Icons</router-link></li>
                            <li>
                                <a target="_blank" href="https://cork-vue.sbthemes.com"> Documentation </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</template>

<script setup>
    import { onMounted, ref, reactive } from 'vue';
    import { useI18n } from 'vue-i18n';
    import { useStore } from 'vuex';
    import axios from 'axios';
    const store = useStore();

    const selectedLang = ref(null);
    const countryList = ref(store.state.countryList);
    const currentUser = ref(null);

    // const isPopupOpen = ref(false);
    const currentPassword = ref('');
    const newPassword = ref('');

    const i18n = reactive(useI18n());

    onMounted(() => {
        selectedLang.value = window.$appSetting.toggleLanguage();
        toggleMode();
        fetchData();
    });

    const openPopup = () => {
        window.location.href = '/change-pw';
    };

    const toggleMode = (mode) => {
        window.$appSetting.toggleMode(mode);
    };

    const changeLanguage = (item) => {
        selectedLang.value = item;
        i18n.locale = item.code;
        window.$appSetting.toggleLanguage(item);
    };
    
    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('permission');
        window.location.href = "/";
    };
    
    const fetchData = () => {
        const token = localStorage.getItem('token');
        axios.get('http://localhost:8081/api/current-user', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            }
        }).then(response => {
             currentUser.value = response.data;
        }).catch(error => {
            console.error(error);
        });
    }
</script>
