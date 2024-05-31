import type {Config} from 'tailwindcss'

export const themeColors = {
    primary: {
        base: "rgba(var(--primary-base), <alpha-value>)",
        lighter: "rgba(var(--primary-lighter), <alpha-value>)",
    },
    secondary: {
        base: "rgba(var(--secondary-base), <alpha-value>)",
    },
    background: {
        base: "rgba(var(--background-base), <alpha-value>)",
        accent: "rgba(var(--background-accent), <alpha-value>)",
        text: "rgba(var(--background-text), <alpha-value>)",
        hover: "rgba(var(--background-hover), <alpha-value>)",
    },
    nav: {
        text: "rgba(var(--nav-text), <alpha-value>)",
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
                overflow: "auto",
            }
        })
    }],
} satisfies Config

