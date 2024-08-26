package com.primejmoordb.application;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MultiColumnLayoutView implements Serializable {

    private final String block1 = "<div class=\"min-h-screen flex relative lg:static surface-ground\">\n" +
            "    <div id=\"app-sidebar\"\n" +
            "         class=\"h-full lg:h-auto hidden lg:block flex-shrink-0 absolute lg:static left-0 top-0 z-1 border-right-1 surface-border w-full md:w-auto\">\n" +
            "        <div class=\"flex h-full\">\n" +
            "            <div class=\"flex flex-column h-full bg-indigo-500 flex-shrink-0 select-none\">\n" +
            "                <div class=\"flex align-items-center justify-content-center flex-shrink-0 bg-indigo-600\"\n" +
            "                     style=\"height:60px\">\n" +
            "                    <p:graphicImage name=\"images/blocks/logos/hyper-light.svg\" library=\"primeblocks-app\"\n" +
            "                                    alt=\"Image\" height=\"30\"/>\n" +
            "                </div>\n" +
            "                <div class=\"overflow-y-auto mt-3\">\n" +
            "                    <ul class=\"list-none py-3 px-2 m-0\" data-pb-tablist=\"multicolumn\">\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors bg-indigo-700\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab1', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-home text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab2', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-bookmark text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab3', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-users text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab4', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-comments text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab5', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-calendar text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer p-3 lg:justify-content-center hover:bg-indigo-400 border-round text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                               data-pb-tab=\"{toggleClass: 'bg-indigo-700', selector: '#tab6', selectorToggleClass: 'hidden'}\">\n" +
            "                                <i class=\"pi pi-cog text-lg\"/>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "                <div class=\"mt-auto\">\n" +
            "                    <hr class=\"mb-3 mx-3 border-top-1 border-bottom-none border-indigo-300\"/>\n" +
            "                    <a class=\"m-3 flex align-items-center cursor-pointer py-1 px-0 justify-content-center hover:bg-indigo-400 border-round text-300 hover:text-0\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:graphicImage name=\"images/blocks/avatars/circle/avatar-f-1.png\"\n" +
            "                                        library=\"primeblocks-app\" alt=\"Image\"\n" +
            "                                        style=\"width: 24px; height: 24px\"/>\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"flex flex-column surface-section p-4 overflow-y-auto flex-shrink-0 flex-grow-1 md:flex-grow-0\"\n" +
            "                 style=\"width:250px\">\n" +
            "                <div class=\"justify-content-end mb-3 flex lg:hidden\">\n" +
            "                    <p:commandButton type=\"button\" icon=\"pi pi-times\"\n" +
            "                                     styleClass=\"rounded-button ui-button-flat ui-button-plain\"\n" +
            "                                     pt:data-pb-styleclass=\"{ selector: '#app-sidebar', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutleft' }\"/>\n" +
            "                </div>\n" +
            "                <div class=\"border-2 border-dashed surface-border border-round flex-auto\">\n" +
            "                    <div id=\"tab1\" class=\"p-3 font-medium text-xl text-900\">Dashboard\n" +
            "                    </div>\n" +
            "                    <div id=\"tab2\" class=\"p-3 font-medium text-xl text-900 hidden\">Bookmarks\n" +
            "                    </div>\n" +
            "                    <div id=\"tab3\" class=\"p-3 font-medium text-xl text-900 hidden\">Team\n" +
            "                    </div>\n" +
            "                    <div id=\"tab4\" class=\"p-3 font-medium text-xl text-900 hidden\">Messages\n" +
            "                    </div>\n" +
            "                    <div id=\"tab5\" class=\"p-3 font-medium text-xl text-900 hidden\">Calendar\n" +
            "                    </div>\n" +
            "                    <div id=\"tab6\" class=\"p-3 font-medium text-xl text-900 hidden\">Settings\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"min-h-screen flex flex-column relative flex-auto\">\n" +
            "        <div class=\"flex justify-content-between align-items-center px-5 surface-section border-bottom-1 surface-border relative lg:static\"\n" +
            "             style=\"height:60px\">\n" +
            "            <div class=\"flex\">\n" +
            "                <a class=\"cursor-pointer block lg:hidden text-700 mr-3\"\n" +
            "                   data-pb-styleclass=\"{ selector: '#app-sidebar', enterClass: 'hidden', enterActiveClass: 'fadeinleft', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutleft'}\">\n" +
            "                    <i class=\"pi pi-bars text-4xl\"/>\n" +
            "                </a>\n" +
            "                <span class=\"ui-input-icon-left\">\n" +
            "                    <i class=\"pi pi-search\"/>\n" +
            "                    <p:inputText type=\"search\" styleClass=\"border-none\" placeholder=\"Search\"/>\n" +
            "                </span>\n" +
            "            </div>\n" +
            "            <a class=\"cursor-pointer block lg:hidden text-700\"\n" +
            "               data-pb-styleclass=\"{ selector: '@next', enterClass: 'hidden', enterActiveClass: 'fadein', leaveToClass: 'hidden', leaveActiveClass: 'fadeout', hideOnOutsideClick: true }\">\n" +
            "                <i class=\"pi pi-ellipsis-v text-2xl\"/>\n" +
            "            </a>\n" +
            "            <ul class=\"list-none p-0 m-0 hidden lg:flex lg:align-items-center select-none lg:flex-row\n" +
            "                surface-section border-1 lg:border-none surface-border right-0 top-100 z-1 shadow-2 lg:shadow-none absolute lg:static\">\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <i class=\"pi pi-inbox text-base lg:text-2xl mr-2 lg:mr-0\"/>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Inbox</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:badge severity=\"danger\">\n" +
            "                            <i class=\"pi pi-bell text-base lg:text-2xl\"/>\n" +
            "                        </p:badge>\n" +
            "                        <span class=\"block lg:hidden font-medium ml-2 lg:ml-0\">Notifications</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li class=\"border-top-1 surface-border lg:border-top-none\">\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:graphicImage name=\"images/blocks/avatars/circle/avatar-f-1.png\"\n" +
            "                                        library=\"primeblocks-app\" alt=\"Image\" styleClass=\"mr-3 lg:mr-0\"\n" +
            "                                        style=\"width: 32px; height: 32px\"/>\n" +
            "                        <div class=\"block lg:hidden\">\n" +
            "                            <div class=\"text-900 font-medium\">Josephine Lillard</div>\n" +
            "                            <span class=\"text-600 font-medium text-sm\">Marketing Specialist</span>\n" +
            "                        </div>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "        <div class=\"p-5 flex flex-column flex-auto\">\n" +
            "            <div class=\"border-2 border-dashed border-round surface-border surface-section flex-auto\">\n" +
            "\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>" ;

    private final String block2 = "<div class=\"min-h-screen flex relative lg:static surface-ground\">\n" +
            "    <div id=\"app-sidebar-9\" class=\"h-screen surface-section hidden lg:block flex-shrink-0 absolute lg:static left-0 top-0 z-1 border-right-1 surface-border w-18rem lg:w-4rem select-none\">\n" +
            "        <div class=\"flex flex-column h-full\">\n" +
            "            <div class=\"flex align-items-center justify-content-center flex-shrink-0 bg-cyan-500\" style=\"height:60px\">\n" +
            "                <p:graphicImage name=\"images/blocks/logos/hyper-light.svg\"\n" +
            "                                        library=\"primeblocks-app\" alt=\"Image\" height=\"30\"/>\n" +
            "            </div>\n" +
            "            <div class=\"mt-3\">\n" +
            "                <ul class=\"list-none p-0 m-0\" data-pb-tablist=\"narrowSidebar\">\n" +
            "                    <li>\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-cyan-600 border-left-2 border-transparent border-cyan-600 hover:border-300 transition-duration-150 transition-colors\"\n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\">\n" +
            "                            <i class=\"pi pi-home mr-2 lg:mr-0 text-base lg:text-2xl\"></i>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Home</span>\n" +
            "                        </a>\n" +
            "                    </li>\n" +
            "                    <li>\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\"\n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\">\n" +
            "                            <i class=\"pi pi-search mr-2 lg:mr-0 text-base lg:text-2xl\"></i>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Search</span>\n" +
            "                        </a>\n" +
            "                    </li>\n" +
            "                    <li>\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\"\n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\">\n" +
            "                            <i class=\"pi pi-users mr-2 lg:mr-0 text-base lg:text-2xl\"></i>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Team</span>\n" +
            "                        </a>\n" +
            "                    </li>\n" +
            "                    <li class=\"relative\">\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\" \n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\"\n" +
            "                            data-pb-styleclass=\"{ selector: '@next', enterClass: 'hidden', leaveToClass: 'hidden', hideOnOutsideClick: true}\">\n" +
            "                            <p:badge severity=\"danger\" value=\"3\">\n" +
            "                                <i class=\"pi pi-chart-line mr-2 lg:mr-0 text-base lg:text-2xl\" />\n" +
            "                            </p:badge>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Reports</span>\n" +
            "                            <i class=\"pi pi-chevron-down ml-auto lg:hidden\"></i>\n" +
            "                        </a>\n" +
            "                        <ul class=\"list-none pl-3 pr-0 py-0 lg:p-3 m-0 hidden overflow-y-hidden transition-all transition-duration-400 transition-ease-in-out static border-round-right lg:absolute left-100 top-0 z-1 surface-overlay shadow-none lg:shadow-2 w-full lg:w-15rem\">\n" +
            "                            <li>\n" +
            "                                <a class=\"flex align-items-center cursor-pointer p-3 hover:surface-100 hover:text-900 border-round text-600 hover:text-700 transition-duration-150 transition-colors\"\n" +
            "                                    data-pb-styleclass=\"{ selector: '@next', toggleClass: 'hidden'}\">\n" +
            "                                    <i class=\"pi pi-chart-line mr-2\"></i>\n" +
            "                                    <span class=\"font-medium\">Revenue</span>\n" +
            "                                    <i class=\"pi pi-chevron-down ml-auto\"></i>\n" +
            "                                </a>\n" +
            "                                <ul class=\"list-none py-0 pl-3 pr-0 m-0 hidden overflow-y-hidden transition-all transition-duration-400 transition-ease-in-out\">\n" +
            "                                    <li>\n" +
            "                                        <a class=\"flex align-items-center cursor-pointer p-3 hover:surface-100 hover:text-900 border-round text-600 hover:text-700 transition-duration-150 transition-colors\">\n" +
            "                                            <i class=\"pi pi-table mr-2\"></i>\n" +
            "                                            <span class=\"font-medium\">View</span>\n" +
            "                                        </a>\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        <a class=\"flex align-items-center cursor-pointer p-3 hover:surface-100 hover:text-900 border-round text-600 hover:text-700 transition-duration-150 transition-colors\">\n" +
            "                                            <i class=\"pi pi-search mr-2\"></i>\n" +
            "                                            <span class=\"font-medium\">Search</span>\n" +
            "                                        </a>\n" +
            "                                    </li>\n" +
            "                                </ul>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <a class=\"flex align-items-center cursor-pointer p-3 hover:surface-100 hover:text-900 border-round text-600 hover:text-700 transition-duration-150 transition-colors\">\n" +
            "                                    <i class=\"pi pi-chart-line mr-2\"></i>\n" +
            "                                    <span class=\"font-medium\">Expenses</span>\n" +
            "                                </a>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </li>\n" +
            "                    <li>\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\"\n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\">\n" +
            "                            <i class=\"pi pi-calendar mr-2 lg:mr-0 text-base lg:text-2xl\"></i>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Events</span>\n" +
            "                        </a>\n" +
            "                    </li>\n" +
            "                    <li>\n" +
            "                        <a class=\"flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\"\n" +
            "                            data-pb-tab=\"{toggleClass: 'border-cyan-600 text-cyan-600'}\">\n" +
            "                            <i class=\"pi pi-cog mr-2 lg:mr-0 text-base lg:text-2xl\"></i>\n" +
            "                            <span class=\"font-medium inline text-base lg:text-xs lg:hidden\">Options</span>\n" +
            "                        </a>\n" +
            "                    </li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "            <div class=\"mt-auto\">\n" +
            "                <hr class=\"mx-3 border-top-1 border-none surface-border\" />\n" +
            "                <a class=\"my-3 flex flex-row lg:flex-column align-items-center cursor-pointer p-3 lg:justify-content-center text-600 border-left-2 border-transparent hover:border-300 transition-duration-150 transition-colors\">\n" +
            "                    <i class=\"pi pi-sign-out mr-2 lg:mr-0 text-base lg:text-2xl text-pink-500\"></i>\n" +
            "                    <span class=\"font-medium inline lg:hidden\">Sign Out</span>\n" +
            "                </a>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"min-h-screen flex flex-column relative flex-auto\">\n" +
            "        <div class=\"flex justify-content-between align-items-center px-5 surface-section relative lg:static border-bottom-1 surface-border\" style=\"height:60px\">\n" +
            "            <div class=\"flex\">\n" +
            "                <a class=\"cursor-pointer block lg:hidden text-700 mr-3\" \n" +
            "                    data-pb-styleclass=\"{ selector: '#app-sidebar-9', enterClass: 'hidden', enterActiveClass: 'fadeinleft', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutleft', hideOnOutsideClick: true }\">\n" +
            "                    <i class=\"pi pi-bars text-4xl\"></i>\n" +
            "                </a>\n" +
            "                <span class=\"ui-input-icon-left\">\n" +
            "                    <i class=\"pi pi-search\"/>\n" +
            "                    <p:inputText type=\"search\" styleClass=\"border-none\" placeholder=\"Search\"/>\n" +
            "                </span>\n" +
            "            </div>\n" +
            "            <a class=\"cursor-pointer block lg:hidden text-700 ml-auto\" \n" +
            "                data-pb-styleclass=\"{ selector: '#topbarmenu', enterClass: 'hidden', enterActiveClass: 'fadein', leaveToClass: 'hidden', leaveActiveClass: 'fadeout', hideOnOutsideClick: true }\">\n" +
            "                <i class=\"pi pi-ellipsis-v text-2xl\"></i>\n" +
            "            </a>\n" +
            "            <ul id=\"topbarmenu\" class=\"list-none p-0 m-0 hidden lg:flex lg:align-items-center select-none lg:flex-row lg:ml-auto\n" +
            "                surface-section border-1 lg:border-none surface-border right-0 top-100 z-1 shadow-2 lg:shadow-none absolute lg:static\">\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <i class=\"pi pi-inbox text-base lg:text-2xl mr-2 lg:mr-0\"></i>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Inbox</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:badge severity=\"danger\">\n" +
            "                            <i class=\"pi pi-bell text-base lg:text-2xl mr-2 lg:mr-0\"></i>\n" +
            "                        </p:badge>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Notifications</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li class=\"border-top-1 surface-border lg:border-top-none\">\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:graphicImage name=\"images/blocks/avatars/circle/avatar-f-1.png\" library=\"primeblocks-app\"\n" +
            "                                    alt=\"Image\" style=\"width: 32px; height: 32px\" styleClass=\"mr-3 lg:mr-0\" />\n" +
            "                        <div class=\"block lg:hidden\">\n" +
            "                            <div class=\"text-900 font-medium\">Amy Elsner</div>\n" +
            "                            <span class=\"text-600 font-medium text-sm\">Marketing Specialist</span>\n" +
            "                        </div>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "            <a class=\"cursor-pointer block text-700 ml-3\" data-pb-styleclass=\"{ selector: '#rightsidebar', enterClass: 'hidden', enterActiveClass: 'fadeinright', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutright', hideOnOutsideClick: true }\">\n" +
            "                <i class=\"pi pi-arrow-left text-2xl\"></i>\n" +
            "            </a>\n" +
            "        </div>\n" +
            "        <div class=\"p-5 flex flex-auto\">\n" +
            "            <div class=\"border-2 border-dashed surface-border border-round surface-section flex-auto\">\n" +
            "                \n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div id=\"rightsidebar\" class=\"surface-overlay shadow-2 hidden absolute right-0 w-20rem animation-duration-150 animation-ease-in-out\" style=\"top:60px;height:calc(100% - 60px)\">\n" +
            "            <div class=\"flex flex-column h-full p-4\">\n" +
            "                <span class=\"text-xl font-medium text-900 mb-3\">Right Sidebar</span>\n" +
            "                <div class=\"border-2 border-dashed surface-border border-round surface-section flex-auto\">\n" +
            "                    \n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>\n" ;

    private final String block3 = "<div class=\"min-h-screen flex relative lg:static surface-ground\">\n" +
            "    <div id=\"app-sidebar-2\" class=\"h-full lg:h-auto hidden lg:block flex-shrink-0 absolute lg:static left-0 top-0 z-1 border-right-1 surface-border md:w-auto\">\n" +
            "        <div class=\"flex h-full\">\n" +
            "            <div class=\"flex flex-column h-full bg-indigo-900 flex-shrink-0 select-none\">\n" +
            "                <div class=\"flex align-items-center justify-content-center flex-shrink-0\" style=\"height:60px\">\n" +
            "                    <p:graphicImage name=\"images/blocks/logos/hyper-light.svg\" library=\"primeblocks-app\" alt=\"Image\" height=\"30\"/>\n" +
            "                </div>\n" +
            "                <div class=\"overflow-y-auto mt-3\">\n" +
            "                    <ul class=\"list-none py-3 pl-2 pr-0 m-0\" data-pb-tablist=\"multicolumn\">\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                                data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab1', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-home text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li> \n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\" \n" +
            "                            data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab2', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-bookmark text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\" \n" +
            "                                data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab3', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-users text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                                data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab4', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-comments text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors\"\n" +
            "                                data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab5', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-calendar text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                        <li class=\"mb-2\">\n" +
            "                            <a class=\"flex align-items-center cursor-pointer py-3 pl-0 pr-2 justify-content-center hover:bg-indigo-600 text-indigo-100 hover:text-indigo-50 transition-duration-150 transition-colors bg-indigo-500\" \n" +
            "                                data-pb-tab=\"{toggleClass: 'bg-indigo-500', selector: '#mc_tab6', selectorToggleClass: 'hidden'}\" style=\"border-top-left-radius: 30px;border-bottom-left-radius: 30px;\">\n" +
            "                                <i class=\"pi pi-cog text-xl\"></i>\n" +
            "                            </a>\n" +
            "                        </li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "                <div class=\"mt-auto\">\n" +
            "                    <hr class=\"mb-3 mx-2 border-top-1 border-none border-indigo-300\" />\n" +
            "                    <a class=\"m-3 flex align-items-center cursor-pointer p-2 justify-content-center hover:bg-indigo-600 border-round text-300 hover:text-0\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:graphicImage name=\"images/blocks/avatars/circle/avatar-f-1.png\" library=\"primeblocks-app\"\n" +
            "                        alt=\"Image\" style=\"width: 24px; height: 24px\" />\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"flex flex-column bg-indigo-500 p-4 overflow-y-auto flex-shrink-0\" style=\"width:300px\">\n" +
            "                <div class=\"justify-content-end mb-3 flex lg:hidden\">\n" +
            "                    <button icon=\"pi pi-times\" class=\"cursor-pointer text-white appearance-none bg-transparent border-none inline-flex justify-content-center align-items-center border-circle hover:bg-indigo-600 transition-duration-150 transition-colors\"  \n" +
            "                    style=\"width:2rem;height:2rem\" \n" +
            "                    pt:data-pb-styleclass=\"{ selector: '#app-sidebar-2', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutleft' }\">\n" +
            "                </button>\n" +
            "                </div>\n" +
            "\n" +
            "\n" +
            "                <div class=\"border-round flex-auto\">\n" +
            "                    <div id=\"mc_tab1\" class=\"p-3 font-medium text-2xl text-white hidden\">Dashboard</div>\n" +
            "                    <div id=\"mc_tab2\" class=\"p-3 font-medium text-2xl text-white hidden\">Bookmarks</div>\n" +
            "                    <div id=\"mc_tab3\" class=\"p-3 font-medium text-2xl text-white hidden\">Team</div>\n" +
            "                    <div id=\"mc_tab4\" class=\"p-3 font-medium text-2xl text-white hidden\">Messages</div>\n" +
            "                    <div id=\"mc_tab5\" class=\"p-3 font-medium text-2xl text-white hidden\">Calendar</div>\n" +
            "                    <div id=\"mc_tab6\">\n" +
            "                        <div class=\"p-3 font-medium text-2xl text-white mb-5\">Settings</div>\n" +
            "                        <ul class=\"list-none p-0 m-0 text-white\">\n" +
            "                            <li class=\"mb-3 flex align-items-start bg-indigo-700 p-3\" style=\"border-radius: 12px\">\n" +
            "                                <i class=\"pi pi-user text-xl mr-3 \"></i>\n" +
            "                                <div class=\"flex flex-column\">\n" +
            "                                    <span>Account</span>\n" +
            "                                    <p class=\"mt-2 mb-0 line-height-3 text-indigo-200\">Accumsan sit amet nulla facilisi morbi tempus iaculis.</p>\n" +
            "                                </div>\n" +
            "                            </li>\n" +
            "                            <li class=\"mb-3 flex align-items-start p-3\" style=\"border-radius: 12px\">\n" +
            "                                <i class=\"pi pi-inbox text-xl mr-3 \"></i>\n" +
            "                                <div class=\"flex flex-column\">\n" +
            "                                    <span>Inbox</span>\n" +
            "                                    <p class=\"mt-2 mb-0 line-height-3 text-indigo-200\">Condimentum vitae sapien pellentesque habitant.</p>\n" +
            "                                </div>\n" +
            "                            </li>\n" +
            "                            <li class=\"mb-3 flex align-items-start p-3\" style=\"border-radius: 12px\">\n" +
            "                                <i class=\"pi pi-credit-card text-xl mr-3 \"></i>\n" +
            "                                <div class=\"flex flex-column\">\n" +
            "                                    <span>Sales</span>\n" +
            "                                    <p class=\"mt-2 mb-0 line-height-3 text-indigo-200\">Egestas integer eget aliquet nibh praesent tristique.</p>\n" +
            "                                </div>\n" +
            "                            </li>\n" +
            "                            <li class=\"mb-3 flex align-items-start p-3\" style=\"border-radius: 12px\">\n" +
            "                                <i class=\"pi pi-lock text-xl mr-3 \"></i>\n" +
            "                                <div class=\"flex flex-column\">\n" +
            "                                    <span>Privacy</span>\n" +
            "                                    <p class=\"mt-2 mb-0 line-height-3 text-indigo-200\">In ante metus dictum at tempor commodo ullamcorper a lacus.</p>\n" +
            "                                </div>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"min-h-screen flex flex-column relative flex-auto\">\n" +
            "        <div class=\"flex justify-content-between lg:justify-content-start align-items-center px-5 surface-section border-bottom-1 surface-border relative lg:static\" style=\"height:60px\">\n" +
            "            <div class=\"flex\">\n" +
            "                <a class=\"cursor-pointer block lg:hidden text-700 mr-3\" \n" +
            "                data-pb-styleclass=\"{ selector: '#app-sidebar-2', enterClass: 'hidden', enterActiveClass: 'fadeinleft', leaveToClass: 'hidden', leaveActiveClass: 'fadeoutleft', hideOnOutsideClick: true}\">\n" +
            "                    <i class=\"pi pi-bars text-4xl\"></i>\n" +
            "                </a>\n" +
            "            </div>\n" +
            "            <p:graphicImage name=\"images/blocks/logos/hyper.svg\" library=\"primeblocks-app\"\n" +
            "                                    alt=\"Image\" height=\"30\" styleClass=\"block lg:hidden\"/>\n" +
            "            <a class=\"cursor-pointer block lg:hidden text-700\" \n" +
            "                data-pb-styleclass=\"{ selector: '@next', enterClass: 'hidden', enterActiveClass: 'fadein', leaveToClass: 'hidden', leaveActiveClass: 'fadeout', hideOnOutsideClick: true}\">\n" +
            "                <i class=\"pi pi-ellipsis-v text-2xl\"></i>\n" +
            "            </a>\n" +
            "            <ul class=\"list-none p-0 m-0 hidden lg:flex lg:align-items-center select-none lg:flex-row lg:w-full\n" +
            "                surface-section border-1 lg:border-none surface-border right-0 top-100 z-1 shadow-2 lg:shadow-none absolute lg:static\">\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <i class=\"pi pi-inbox text-base lg:text-2xl mr-2 lg:mr-0\"></i>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Inbox</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <i class=\"pi pi-star text-base lg:text-2xl mr-2 lg:mr-0\"></i>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Favorites</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <i class=\"pi pi-user text-base lg:text-2xl mr-2 lg:mr-0\"></i>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Account</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li>\n" +
            "                    <a class=\"flex p-3 lg:px-3 lg:py-2 align-items-center text-600 hover:text-900 hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:badge severity=\"danger\">\n" +
            "                            <i class=\"pi pi-bell text-base lg:text-2xl mr-2 lg:mr-0\" />\n" +
            "                        </p:badge>\n" +
            "                        <span class=\"block lg:hidden font-medium\">Notifications</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "                <li class=\"border-top-1 surface-border lg:border-top-none lg:ml-auto\">\n" +
            "                    <a  class=\"flex p-3 lg:px-3 lg:py-2 align-items-center hover:surface-100 font-medium border-round cursor-pointer\n" +
            "                        transition-duration-150 transition-colors\">\n" +
            "                        <p:graphicImage name=\"images/blocks/avatars/circle/avatar-f-1.png\" library=\"primeblocks-app\"\n" +
            "                                    alt=\"Image\" style=\"width: 32px; height: 32px\" styleClass=\"mr-3 lg:mr-0\"/>\n" +
            "                        <div class=\"block lg:hidden\">\n" +
            "                            <div class=\"text-900 font-medium\">Josephine Lillard</div>\n" +
            "                            <span class=\"text-600 font-medium text-sm\">Marketing Specialist</span>\n" +
            "                        </div>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "        <div class=\"p-5 flex flex-column flex-auto\">\n" +
            "            <div class=\"border-2 border-dashed border-round surface-border surface-section flex-auto\">\n" +
            "                \n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>\n" ;

    public String getBlock1() {
        return block1;
    }

    public String getBlock2() {
        return block2;
    }

    public String getBlock3() {
        return block3;
    }
}
