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
        accent: "rgba(var(--background-accent), <alpha-value>)",
        text: "var(--background-text)",
        hover: "var(--background-hover)",
    },
    nav: {
        text: "var(--nav-text)",
    }
};

export default {
    content: ['./src/**/*.{js,ts,jsx,tsx}'],
    theme: {
        extend: {
            colors: {...themeColors},
            height: {
                fullWithoutNav: `calc(200px+500px)`,
            }
        },
    },
    // @ts-ignore
    plugins: [({addUtilities}) => {
        addUtilities({
            '.content-container': {
                height: `calc(100dvh - 98px)`,
            }
        })
    }],
} satisfies Config

