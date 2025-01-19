class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
        <header class="header" data-header-theme="light">
            <div class="header-primary">
                <div class="header-primary-wrap">
                    <h1>
                        <a class="logo" href="/kbinnovationhub"><span class="blind">KB Innovation HUB</span></a>
                    </h1>
                    <div class="gnb">
                        <nav class="nav">
                            <ul class="depth1">
                                <li>
                                    <div class="title">KB Innovation HUB</div>
                                    <ul class="depth2">
                                        <li><a href="/kbinnovationhub">HUB센터 소개</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">스타트업 육성</div>
                                    <ul class="depth2">
                                        <li><a href="/kbinnovationhub/nurture/domestic/info">국내 프로그램</a></li>
                                        <li><a href="/kbinnovationhub/nurture/global/info">글로벌 프로그램</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">KB스타터스</div>
                                    <ul class="depth2">
                                        <li><a href="/kbinnovationhub/starters/apply/apply_main">지원하기</a></li>
                                        <li><a href="/kbinnovationhub/starters/portfolio/list">포트폴리오</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">커뮤니티</div>
                                    <ul class="depth2">
                                        <li><a href="/kbinnovationhub/community/notice/list">공지사항</a></li>
                                        <li><a href="/kbinnovationhub/community/hub/list">HUB센터 소식</a></li>
                                        <li><a href="/kbinnovationhub/community/faq/list">FAQ</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">스타트업 정보</div>
                                    <ul class="depth2">
                                        <li><a href="/kbinnovationhub/startup/list">정보검색</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                        <div class="close">
                            <a href="javascript:">
                                <span class="circle"></span>
                                <span class="blind">전체메뉴 닫기</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="background"></div>
                <div class="hamburger">
                    <a href="javascript:">
                        <span class="circle"></span>
                        <span class="blind">전체메뉴 열기</span>
                    </a>
                </div>
            </div>
        </header>
      `;
    }
}
customElements.define('app-header', HeaderComponent);