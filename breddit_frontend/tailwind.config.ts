import type {Config} from 'tailwindcss'

export const themeColors = {
    primary: {
        base: "var(--primary-base)",
        lighter: "var(--primary-lighter)",
    },
    secondary: {
        base: "var(--secondary-base)",
    },
    background: {
        base: "var(--background-base)",
        accent: "var(--background-accent)",
        text: "var(--background-text)",
        hover: "var(--background-hover)",
    },
};

export default {
    content: ['./src/**/*.{js,ts,jsx,tsx}'],
    theme: {
        extend: {
            colors: {...themeColors},
        },
    },
    plugins: [],
} satisfies Config

