class HeaderComponent extends HTMLElement {
    connectedCallback() {
        const path = window.staticPath || '';
        let header_menu = `
                                <ul class="depth1">
                                    <li>
                                        <div class="title">KB Innovation HUB</div>
                                        <ul class="depth2">
                                            <li><a href="` + path + `">HUB센터 소개</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <div class="title">스타트업 육성</div>
                                        <ul class="depth2">
                                            <li><a href="` + path + `nurture/domestic/info">국내 프로그램</a></li>
                                            <li><a href="` + path + `nurture/global/info">글로벌 프로그램</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <div class="title">KB스타터스</div>
                                        <ul class="depth2">
                                            <li><a href="` + path + `starters/apply/apply_main">지원하기</a></li>
                                            <li><a href="` + path + `starters/portfolio/list">포트폴리오</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <div class="title">커뮤니티</div>
                                        <ul class="depth2">
                                            <li><a href="` + path + `community/notice/list">공지사항</a></li>
                                            <li><a href="` + path + `community/hub/list">HUB센터 소식</a></li>
                                            <li><a href="` + path + `community/faq/list">FAQ</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <div class="title">스타트업 정보</div>
                                        <ul class="depth2">
                                            <li><a href="` + path + `startup/list">정보검색</a></li>
                                        </ul>
                                    </li>
                                </ul>
                                `;

        if(menuData) {
            header_menu = createMenu(menuData);
        }

        this.innerHTML = `
        <header class="header" data-header-theme="light">
            <div class="header-primary">
                <div class="header-primary-wrap">
                    <h1>
                        <a class="logo" href="` + path + `"><span class="blind">KB Innovation HUB</span></a>
                    </h1>
                    <div class="gnb">
                        <nav class="nav">
                            ${header_menu}
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

function createMenu(menuData) {
    const depth1Ul = document.createElement('ul');
    depth1Ul.className = 'depth1';

    // 1단계 메뉴 필터링
    const depth1Menus = menuData.filter(menu => menu.menu_depth === 1);

    depth1Menus.forEach(depth1Menu => {

        const depth1Li = document.createElement('li');
        const titleDiv = document.createElement('div');
        titleDiv.className = 'title';
        titleDiv.textContent = depth1Menu.menu_nm;
        depth1Li.appendChild(titleDiv);

        const depth2Ul = document.createElement('ul');
        depth2Ul.className = 'depth2';

        // 2단계 메뉴 필터링
        const depth2Menus = menuData.filter(menu => menu.menu_up_sn === depth1Menu.menu_sn);

        depth2Menus.forEach(depth2Menu => {
            const depth2Li = document.createElement('li');
            const anchor = document.createElement('a');
            if(depth2Menu.menu_link !== '/kbinnovationhub') {
                anchor.href = depth2Menu.menu_link.replace('/kbinnovationhub', window.staticPath.replace(/\/+$/, ''));
            }else{
                anchor.href = depth2Menu.menu_link.replace('/kbinnovationhub', window.staticPath !== '/' ? window.staticPath.replace(/\/+$/, '') : window.staticPath);
            }
            anchor.textContent = depth2Menu.menu_nm;
            depth2Li.appendChild(anchor);
            depth2Ul.appendChild(depth2Li);
        });

        depth1Li.appendChild(depth2Ul);
        depth1Ul.appendChild(depth1Li);
    });
    return depth1Ul.outerHTML;
}

customElements.define('app-header', HeaderComponent);