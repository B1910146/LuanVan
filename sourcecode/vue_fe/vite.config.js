import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";
import vueI18n from "@intlify/vite-plugin-vue-i18n";
import cors from 'cors';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueI18n({
            include: path.resolve(__dirname, "./src/locales/**"),
        }),
    ],
    optimizeDeps: {
        include: ["quill", "nouislider"],
    },
    resolve: {
        alias: [
            {
                find: /^~(.*)$/,
                replacement: "node_modules/$1",
            },
            // {
            //     "@": path.resolve(__dirname, ".src/"),
            // },
        ],
    },
    // server: {
    //     middleware: [
    //         cors({
    //             origin: "*",
    //         }),
    //     ],
    // },
});
