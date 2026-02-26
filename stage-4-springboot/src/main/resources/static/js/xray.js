const XRay = (function() {
    let config = null;
    let isPlaying = false;
    let isSetup = false;

    const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

    function injectDrawerHTML() {
        if (document.getElementById('xray-trigger')) return;

        const html = `
            <button id="xray-trigger">⚙️ View Architecture</button>
            <div id="xray-drawer">
                <div class="xray-header">
                    <h2 id="xray-title">Architecture</h2>
                    <h3 id="xray-module">Module</h3>
                </div>
                
                <div id="xray-menu-view">
                    <div id="xray-impact" style="display: none;">
                        <h4>Architectural Impact</h4>
                        <ul id="xray-impact-list"></ul>
                    </div>
                    <div class="xray-controls">
                        <button id="btn-play-success" class="xray-btn btn-success">▶ Play Success Flow</button>
                        <button id="btn-play-error" class="xray-btn btn-error">▶ Play Error Flow</button>
                        <button id="btn-xray-close" class="xray-btn xray-close">Close Menu</button>
                    </div>
                </div>

                <div id="xray-sim-view">
                    <div class="xray-network">
                        <div class="xray-node">💻<br>Client</div>
                        <div class="xray-node">⚙️<br>Server</div>
                        <div class="xray-node">🗄️<br>DB</div>
                        <div id="xray-dot"></div>
                    </div>
                    <div id="xray-console"></div>
                    <button id="btn-xray-done">Return to Menu</button>
                </div>
            </div>
        `;
        document.body.insertAdjacentHTML('beforeend', html);
    }

    function setupEventListeners() {
        // Open/Close Drawer
        document.getElementById('xray-trigger').addEventListener('click', () => {
            document.getElementById('xray-drawer').classList.add('open');
        });
        document.getElementById('btn-xray-close').addEventListener('click', () => {
            document.getElementById('xray-drawer').classList.remove('open');
        });

        // The "Done" Button (Switches back to Menu View)
        document.getElementById('btn-xray-done').addEventListener('click', () => {
            document.getElementById('xray-sim-view').style.display = 'none';
            document.getElementById('xray-menu-view').style.display = 'flex';
        });

        // Play Buttons
        document.getElementById('btn-play-success').addEventListener('click', () => {
            if(!isPlaying) runSimulation(config.successFlow);
        });
        document.getElementById('btn-play-error').addEventListener('click', () => {
            if(!isPlaying && config.errorFlow) runSimulation(config.errorFlow);
        });
    }

    async function runSimulation(flowArray) {
        if (!flowArray || flowArray.length === 0) return;
        isPlaying = true;
        
        // 1. SWITCH VIEWS: Hide Menu, Show Simulation
        document.getElementById('xray-menu-view').style.display = 'none';
        document.getElementById('xray-sim-view').style.display = 'flex';
        document.getElementById('btn-xray-done').style.display = 'none'; // Hide Done button until finished

        const dot = document.getElementById('xray-dot');
        const consoleOut = document.getElementById('xray-console');
        
        // 2. PRE-RENDER ALL STEPS
        consoleOut.innerHTML = '';
        flowArray.forEach((step, index) => {
            const stepDiv = document.createElement('div');
            stepDiv.className = 'xray-step';
            stepDiv.id = `xray-step-${index}`;
            stepDiv.innerHTML = step.message;
            consoleOut.appendChild(stepDiv);
        });

        dot.className = ''; 

        // 3. RUN THE ANIMATION LOOP
        for (let i = 0; i < flowArray.length; i++) {
            const step = flowArray[i];
            const stepElement = document.getElementById(`xray-step-${i}`);

            dot.className = `pos-${step.location} type-${step.type}`;
            stepElement.classList.add('active');
            stepElement.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            
            await sleep(1500);

            stepElement.classList.remove('active');
            if (step.type === 'error') {
                stepElement.classList.add('error');
            } else {
                stepElement.classList.add('done');
            }
        }

        // 4. FINISH: Show completion message and the "Done" button
        const completeDiv = document.createElement('div');
        completeDiv.className = 'xray-step done';
        completeDiv.style.borderLeftColor = 'transparent';
        completeDiv.innerHTML = `✓ Simulation Complete.`;
        consoleOut.appendChild(completeDiv);
        completeDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        
        document.getElementById('btn-xray-done').style.display = 'block'; // Reveal the return button
        isPlaying = false;
    }

return {
        init: function(userConfig) {
            console.log('1');
            config = userConfig;

            // Extract the setup logic into a reusable function
            const applyConfig = () => {

                // 1. Only inject HTML and listeners the FIRST time XRay.init is called
                if (!isSetup) {
                    injectDrawerHTML();
                    setupEventListeners();
                    isSetup = true;
                }

                console.log('2 - Executed!');

                // 2. Always update the text, because the user might have switched SPA modules
                document.getElementById('xray-title').innerText = config.title;
                document.getElementById('xray-module').innerText = config.module;

                const impactContainer = document.getElementById('xray-impact');
                if (config.impact && config.impact.length > 0) {
                    const impactList = document.getElementById('xray-impact-list');
                    impactContainer.style.display = 'block';

                    let listHTML = '';
                    for(let item of config.impact) {
                        listHTML += `<li>${item}</li>`;
                    }
                    impactList.innerHTML = listHTML;
                } else {
                    impactContainer.style.display = 'none';
                }

                const errorBtn = document.getElementById('btn-play-error');
                if(!config.errorFlow) {
                    if (errorBtn) errorBtn.style.display = 'none';
                } else {
                    if (errorBtn) errorBtn.style.display = 'inline-block';
                }
            };

            // CRITICAL FIX: Check if DOM is already loaded!
            if (document.readyState === 'loading') {
                // For Stages 1-3 (MPA): Wait for DOM to load
                document.addEventListener("DOMContentLoaded", applyConfig);
            } else {
                // For Stage 4 (SPA): DOM is already loaded, run immediately!
                applyConfig();
            }
        }
    };
})();