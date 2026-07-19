const ResearchIllustration = () => (
  <svg
    width="340"
    height="280"
    viewBox="0 0 340 280"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
    aria-hidden="true"
  >
    {/* Central document node */}
    <rect x="130" y="100" width="80" height="100" rx="6" fill="rgba(255,255,255,0.08)" stroke="rgba(255,255,255,0.35)" strokeWidth="1.5" />
    <line x1="145" y1="122" x2="195" y2="122" stroke="rgba(255,255,255,0.5)" strokeWidth="1.5" strokeLinecap="round" />
    <line x1="145" y1="135" x2="195" y2="135" stroke="rgba(255,255,255,0.35)" strokeWidth="1.5" strokeLinecap="round" />
    <line x1="145" y1="148" x2="178" y2="148" stroke="rgba(255,255,255,0.35)" strokeWidth="1.5" strokeLinecap="round" />
    <line x1="145" y1="161" x2="185" y2="161" stroke="rgba(255,255,255,0.25)" strokeWidth="1.5" strokeLinecap="round" />
    <line x1="145" y1="174" x2="170" y2="174" stroke="rgba(255,255,255,0.25)" strokeWidth="1.5" strokeLinecap="round" />

    {/* Top-left node: AI brain */}
    <circle cx="52" cy="60" r="28" fill="rgba(255,255,255,0.06)" stroke="rgba(255,255,255,0.3)" strokeWidth="1.5" />
    <path d="M42 60 Q52 48 62 60 Q52 72 42 60Z" stroke="rgba(255,255,255,0.5)" strokeWidth="1.2" fill="none" />
    <circle cx="52" cy="60" r="4" fill="rgba(255,255,255,0.4)" />

    {/* Top-right node: magnifier */}
    <circle cx="288" cy="55" r="28" fill="rgba(255,255,255,0.06)" stroke="rgba(255,255,255,0.3)" strokeWidth="1.5" />
    <circle cx="284" cy="51" r="10" stroke="rgba(255,255,255,0.5)" strokeWidth="1.5" fill="none" />
    <line x1="291" y1="58" x2="298" y2="65" stroke="rgba(255,255,255,0.5)" strokeWidth="2" strokeLinecap="round" />

    {/* Bottom-left node: graph */}
    <circle cx="48" cy="222" r="28" fill="rgba(255,255,255,0.06)" stroke="rgba(255,255,255,0.3)" strokeWidth="1.5" />
    <polyline points="34,232 42,218 50,225 58,210 66,215" stroke="rgba(255,255,255,0.5)" strokeWidth="1.5" fill="none" strokeLinecap="round" strokeLinejoin="round" />

    {/* Bottom-right node: network */}
    <circle cx="292" cy="222" r="28" fill="rgba(255,255,255,0.06)" stroke="rgba(255,255,255,0.3)" strokeWidth="1.5" />
    <circle cx="292" cy="214" r="4" stroke="rgba(255,255,255,0.5)" strokeWidth="1.2" fill="rgba(255,255,255,0.15)" />
    <circle cx="280" cy="230" r="3.5" stroke="rgba(255,255,255,0.5)" strokeWidth="1.2" fill="rgba(255,255,255,0.15)" />
    <circle cx="304" cy="230" r="3.5" stroke="rgba(255,255,255,0.5)" strokeWidth="1.2" fill="rgba(255,255,255,0.15)" />
    <line x1="292" y1="218" x2="280" y2="227" stroke="rgba(255,255,255,0.35)" strokeWidth="1" />
    <line x1="292" y1="218" x2="304" y2="227" stroke="rgba(255,255,255,0.35)" strokeWidth="1" />
    <line x1="283" y1="230" x2="301" y2="230" stroke="rgba(255,255,255,0.35)" strokeWidth="1" />

    {/* Connector lines from center doc to nodes */}
    <line x1="130" y1="130" x2="80" y2="75" stroke="rgba(255,255,255,0.18)" strokeWidth="1" strokeDasharray="4 4" />
    <line x1="210" y1="130" x2="262" y2="72" stroke="rgba(255,255,255,0.18)" strokeWidth="1" strokeDasharray="4 4" />
    <line x1="130" y1="175" x2="76" y2="208" stroke="rgba(255,255,255,0.18)" strokeWidth="1" strokeDasharray="4 4" />
    <line x1="210" y1="175" x2="265" y2="208" stroke="rgba(255,255,255,0.18)" strokeWidth="1" strokeDasharray="4 4" />

    {/* Floating dots */}
    <circle cx="170" cy="40" r="2.5" fill="rgba(255,255,255,0.25)" />
    <circle cx="200" cy="260" r="2" fill="rgba(255,255,255,0.2)" />
    <circle cx="110" cy="260" r="2" fill="rgba(255,255,255,0.2)" />
    <circle cx="320" cy="140" r="2.5" fill="rgba(255,255,255,0.2)" />
    <circle cx="20" cy="140" r="2.5" fill="rgba(255,255,255,0.2)" />
  </svg>
);

export default ResearchIllustration;
