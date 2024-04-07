type ButtonProps = {
    label: string,
    onClick: () => {},
}

export default function Button({label, onClick}: ButtonProps) {
    return (
        <>
            <button className="h-10 w-15 bg-primary-base text-white rounded-xl bold align-middle transition-all ease-linear delay-50 hover:bg-primary-lighter" onClick={onClick}>{label}</button>
        </>
    )
}