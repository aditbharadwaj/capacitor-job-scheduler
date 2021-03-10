import { WebPlugin } from '@capacitor/core';
import { CallbackError, CapacitorJobSchedulerPlugin } from './definitions';

export class CapacitorJobSchedulerWeb extends WebPlugin implements CapacitorJobSchedulerPlugin {
  constructor() {
    super({
      name: 'CapacitorJobScheduler',
      platforms: ['web'],
    });
  }
  scheduleJob(_callback: (error?: CallbackError) => void): string {
    throw new Error('Method not implemented.');
  }
  cancelJob(): Promise<void> {
    throw new Error('Method not implemented.');
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const CapacitorJobScheduler = new CapacitorJobSchedulerWeb();

export { CapacitorJobScheduler };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorJobScheduler);
