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
    },
    common: {
        gray: "rgba(var(--common-gray), <alpha-value>)"
    }
};

export default {
    content: ['./src/**/*.{js,ts,jsx,tsx}'],
    theme: {
        extend: {
            colors: {...themeColors},
            height: {
                fullWithoutNav: `calc(200px+500px)`,
            },
            keyframes: {
                slideIn: {
                    "0%": {opacity: "0", transform: "translateX(100%)"},
                    "100%": {opacity: "1", transform: "translateX(0)"}
                },
                slideOut: {
                    "0%": {opacity: "1", transform: "translateX(0)"},
                    "100%": {opacity: "0", transform: "translateX(100%)"}
                },
                fadeIn: {
                    "0%": {opacity: "0"},
                    "100%": {opacity: "1"}
                },
                fadeOut: {
                    "0%": {opacity: "1"},
                    "100%": {opacity: "0"}
                }
            },
            animation: {
                slideIn: "slideIn 700ms ease-in-out",
                slideOut: "slideOut 700ms ease-in-out",
                fadeIn: "fadeIn 400ms ease-in-out",
                fadeOut: "fadeOut 400ms ease-in-out",
            },
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

